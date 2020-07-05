package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.maryland;


import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography.Tract;
import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.exceptions.InternetSpeedDataLoaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InternetSpeedDataLoader {


    //=============================================================================================
    // Private Assets
    //=============================================================================================

    private static final String CARROLL_COUNTY_CENSUS_TRACT_PREFIX = "24013";

    /**
     * Name of File
     */
    private static final String INTERNET_SPEED_FILE_NAME = "SPEED_CENSUS_TRACT.csv";

    /**
     * This provides access to logging.
     */
    private final Logger log = LogManager.getLogger(InternetSpeedDataLoader.class);

    /**
     * File that will hold the link to the Census Geographic Data File
     */
    private final File internetSpeedFile;

    /**
     * Field that will hold the data
     */
    private Map<Integer, Record> internetSpeedByCensusTractRaw;


    /**
     * Field that will hold the data
     */
    private Map<String, Record> internetSpeedByCensusTractCollated;


    //=============================================================================================
    // Constructor(s)
    //=============================================================================================

    /**
     * This No Argument constructor Will use the internal file.
     */
    public InternetSpeedDataLoader() {
        // If this is called, we will use the internal file
        this.internetSpeedFile = new File(
                this.getClass().getClassLoader().getResource(INTERNET_SPEED_FILE_NAME).getFile()
        );

        this.internetSpeedByCensusTractRaw = new HashMap<>();
        this.internetSpeedByCensusTractCollated = new HashMap<>();
    }




    //=============================================================================================
    // Major Methods
    //=============================================================================================

    /**
     * Major method to read in the data
     */
    public void readInFile() throws InternetSpeedDataLoaderException {


        //We try to read the lines of the file
        try{
            readLines();
        } catch (Exception ioe){
            //If we get an exception of any type we need to stop execution and throw this information to the user.
            throw new InternetSpeedDataLoaderException("Error parsing in the data!", ioe);
        }
    }

    /**
     * Line reader functionality
     */
    private void readLines() throws IOException {
        //This is a try with resources block.  Inside of it, you have auto-closeable things, like a buffered reader
        // You use this EVERY time there is a resource with auto-closeable abilities.
        try(FileReader fileReader = new FileReader(this.internetSpeedFile); //Here we make the file reader
            BufferedReader reader = new BufferedReader(fileReader)){  //Here we make a buffered reader
            String line = "";  //This will hold the lines from the file reader
            int linePos = 0;  //This will hold the position the data was taken from.
            while((line = reader.readLine()) != null){  //This complicated logic takes a line from the reader
                // and puts it into line.  Then checks to see if the line was null.
                //The line reader will return a null when eof hits.

                //TODO: YOU HAVE TO PUT IN THE LOGIC TO MAKE THIS WORK!
                readAline(line, linePos);
                linePos++;
            }
        }
    }

    /**
     * Method to parse a single line
     */
    private void readAline(String line, int linePos)  {
        if (linePos < 0) {
            throw new InternetSpeedDataLoaderException("Bad Line Position: " + linePos);
        }
        if (linePos < 2 ) {
            return;  // We don't want to read in the header lines!
        }
        String[] lineParts = line.split(","); // Here we split on commas as this file is comma separated.

        String objectId = lineParts[5];
        String censusTract = objectId.substring(0, 11);
        String max = lineParts[8];
        String min = lineParts[7];
        String avg = lineParts[9];
        String count = lineParts[6];

        if (max.isBlank()){
            return;
        }

        Record record = new Record(max, min, avg, count, objectId, censusTract);

        internetSpeedByCensusTractRaw.put(linePos, record);

    }

    public void collate(List<Tract> tracts){

        Map<Integer, Record> intermediaryList = new HashMap<>();

        for(Map.Entry<Integer, Record> entry : internetSpeedByCensusTractRaw.entrySet()){
            String entryCensusTract = entry.getValue().getCensusTract();
            if(entryCensusTract.contains(CARROLL_COUNTY_CENSUS_TRACT_PREFIX)){
               intermediaryList.put(entry.getKey(), entry.getValue());
               log.info(entry.getValue());
            }
        }
        for(Tract tract : tracts) {
            List<Record> thisTractRecords = new ArrayList<>();

            String geoIdOfTract = tract.getGeoIdStr();

            for (Map.Entry<Integer, Record> entry : intermediaryList.entrySet()) {
                if(entry.getValue().getCensusTract().equalsIgnoreCase(geoIdOfTract)){
                    thisTractRecords.add(entry.getValue());
                }
            }

            double min = 0;
            double max = 0;
            double avg = 0;
            double count = 0;
            double num = 0;
            log.info("There are {} records in tract: {}", thisTractRecords.size(), tract.getGeoIdStr());
            for(Record record : thisTractRecords){
                if(min == 0){
                    min = Double.parseDouble(record.getMin());
                } else {
                    min = Math.min(min, Double.parseDouble(record.getMin()));
                }
                max = Math.max(max, Double.parseDouble(record.getMax()));
                avg = avg + Double.parseDouble(record.getAvg());
                count = count + Double.parseDouble(record.getCount());
                num += 1;
            }
            if(num>0) {
                avg = avg / num;
            }
            Record record = new Record(max + "", min + "", avg + "", count + "", null, geoIdOfTract);
            this.internetSpeedByCensusTractCollated.put(geoIdOfTract, record);

            log.info("For Tract: {}, the record is: {}", geoIdOfTract, record);
        }



        log.info("Intermidiary List is: {} long.", intermediaryList.size());
    }

    public Record getSpeedForTract(Tract tract){
        String tractGeoId = tract.getGeoIdStr();
        return this.internetSpeedByCensusTractCollated.get(tractGeoId);
    }


}
