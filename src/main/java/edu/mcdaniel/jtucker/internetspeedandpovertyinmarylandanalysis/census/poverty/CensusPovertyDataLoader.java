package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.poverty;

import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography.Tract;
import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.exceptions.CensusGeographicDataLoaderException;
import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.exceptions.CensusPovertyDataLoaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CensusPovertyDataLoader {

    //=============================================================================================
    // Private Assets
    //=============================================================================================

    /**
     * Name of File
     */
    private static final String POVERTY_RATES_FILE_NAME = "POV_RATES_TRACTS_IN_CARROLL.csv";

    /**
     * This provides access to logging.
     */
    private final Logger log = LogManager.getLogger(CensusPovertyDataLoader.class);

    /**
     * File that will hold the link to the Census Geographic Data File
     */
    private File povertyRatesFile;

    /**
     * Field that will hold the data
     */
    private Map<String, Double> povertyPctByTract;


    //=============================================================================================
    // Constructor(s)
    //=============================================================================================

    /**
     * This No Argument constructor Will use the internal file.
     */
    public CensusPovertyDataLoader() {
        // If this is called, we will use the internal file
        this.povertyRatesFile = new File(
                this.getClass().getClassLoader().getResource(POVERTY_RATES_FILE_NAME).getFile()
        );

        this.povertyPctByTract = new HashMap<>();
    }




    //=============================================================================================
    // Major Methods
    //=============================================================================================

    /**
     * Major method to read in the data
     */
    public void readInFile() {


        //We try to read the lines of the file
        try{
            readLines();
        } catch (Exception ioe){
            //If we get an exception of any type we need to stop execution and throw this information to the user.
            throw new CensusPovertyDataLoaderException("Error parsing in the data!", ioe);
        }
    }

    /**
     * Line reader functionality
     */
    private void readLines() throws IOException {
        //This is a try with resources block.  Inside of it, you have auto-closeable things, like a buffered reader
        // You use this EVERY time there is a resource with auto-closeable abilities.
        try(FileReader fileReader = new FileReader(this.povertyRatesFile); //Here we make the file reader
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
            throw new CensusGeographicDataLoaderException("Bad Line Position: " + linePos);
        }
        if (linePos < 2  || linePos > 39) {
            return;  // We don't want to read in the header lines!
        }
        String[] lineParts = line.split(","); // Here we split on commas as this file is comma separated.

        String id = lineParts[0].replace("\"", "");
        String pct = lineParts[8].replace("\"", "");
        double prct = Double.parseDouble(pct);
        povertyPctByTract.put(id, prct);

        log.info("Census Tract {}, has a {}% poverty rate", id, prct);
    }

    public String getPovertyRateForTract(Tract tract) {
        String tractGeoId = tract.getGeoIdStr();
        String percent = "";

        for(Map.Entry<String, Double> entry : this.povertyPctByTract.entrySet()){
            if(entry.getKey().contains(tractGeoId)){
                percent = entry.getValue() + "%";
            }
        }
        return percent;
    }



    //=============================================================================================
    // Getters and Setters
    //=============================================================================================

}
