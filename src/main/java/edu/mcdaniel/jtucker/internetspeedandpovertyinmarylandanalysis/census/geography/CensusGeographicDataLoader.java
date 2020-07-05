package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography;

import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.exceptions.CensusGeographicDataLoaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
* This class will read a file.  We will manipulate the data later.
*/
public class CensusGeographicDataLoader {

    //=============================================================================================
    // Private Assets
    //=============================================================================================

    /**
     * Name of Tracts File
     */
    private static final String LAT_LONG_OF_TRACTS_TAB = "LAT_LONG_OF_TRACTS.tab";

    /**
     * This provides access to logging.
     */
    private final Logger log = LogManager.getLogger(CensusGeographicDataLoader.class);

    /**
     * File that will hold the link to the Census Geographic Data File
     */
    private File censusDataFile;

    /**
     * Field that will hold the values of State Codes
     */
    private Map<Integer, String> state;

    /**
     * Field that will hold the values of GeoID for the particular geographic tract
     */
    private Map<Integer, String> geoId;

    /**
     * Field that will hold the values of square meters of land
     */
    private Map<Integer, String> aLandM;

    /**
     * Field that will hold the values of square meters of water
     */
    private Map<Integer, String> aWaterM;

    /**
     * Field that will hold the values of Interpolated Latitude
     */
    private Map<Integer, String> interpLat;

    /**
     * Field that will hold the values of Interpolated Longitude
     */
    private Map<Integer, String> interpLong;


    //=============================================================================================
    // Constructor(s)
    //=============================================================================================

    /**
     * This No Argument constructor Will use the internal file.
     */
    public CensusGeographicDataLoader() {
        // If this is called, we will use the internal file
        this.censusDataFile = new File(
                this.getClass().getClassLoader().getResource(LAT_LONG_OF_TRACTS_TAB).getFile()
        );

        this.aLandM = new HashMap<>();
        this.aWaterM = new HashMap<>();
        this.state = new HashMap<>();
        this.geoId = new HashMap<>();
        this.interpLat = new HashMap<>();
        this.interpLong = new HashMap<>();
    }




    //=============================================================================================
    // Major Methods
    //=============================================================================================

    /**
     * This major method initializes the file.
     */
    public void setUp() throws CensusGeographicDataLoaderException {
        if(!validate()){
            throw new CensusGeographicDataLoaderException("Invalid File Setup.");
        }

    }

    /**
     * Major method to read in the data
     */
    public void readInFile() throws CensusGeographicDataLoaderException {


        //We try to read the lines of the file
        try{
            readLines();
        } catch (Exception ioe){
            //If we get an exception of any type we need to stop execution and throw this information to the user.
            throw new CensusGeographicDataLoaderException("Error parsing in the data!", ioe);
        }
    }

    /**
     * Line reader functionality
     */
    private void readLines() throws  IOException {
        //This is a try with resources block.  Inside of it, you have auto-closeable things, like a buffered reader
        // You use this EVERY time there is a resource with auto-closeable abilities.
        try(FileReader fileReader = new FileReader(this.censusDataFile); //Here we make the file reader
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
        if (linePos < 2) {
            return;  // We don't want to read in the header lines!
        }
        String[] lineParts = line.split("\t"); // Here we split on tabs as this file is tab separated.

        //I am expecting that there will be 14 columns, All filled with data.
        if (lineParts.length == 8) {
            String stateVal  = lineParts[0];
            String geoIdVal   = lineParts[1];
            String aland     = lineParts[2];
            String awater    = lineParts[3];
            String lat       = lineParts[6];
            String longitude = lineParts[7];


            //Now to check we have values we are expecting!
            if (state == null || geoId == null || aland == null || awater == null || lat == null || longitude == null) {
                throw new CensusGeographicDataLoaderException("Bad Data in line " + linePos + " Line Value " + line);
            }

            this.state.put(linePos, stateVal);
            this.geoId.put(linePos, geoIdVal);
            this.aLandM.put(linePos, aland);
            this.aWaterM.put(linePos, awater);
            this.interpLat.put(linePos, lat);
            this.interpLong.put(linePos, longitude);

            log.info("Values Extracted: State={}, GeoId={}, aLand={}, aWater={}, lat={}, long={}",
                    stateVal, geoIdVal, aland, awater, lat, longitude);
        }
    }

    //=============================================================================================
    // Minor Methods(s)
    //=============================================================================================

    /**
     * validation method.
     * @return true if valid.
     */
    public boolean validate(){
        return this.censusDataFile != null && this.censusDataFile.canRead();
    }


    //=============================================================================================
    // Getters and Setters
    //=============================================================================================

    /**
     * Just to get the File name.
     */
    public String getFileName(){
        return this.censusDataFile.getName();
    }

    /**
     * Just to get the file itself
     */
    public File getFile(){
        return this.censusDataFile;
    }

    public Map<Integer, String> getState() {
        return state;
    }

    public void setState(Map<Integer, String> state) {
        this.state = state;
    }

    public Map<Integer, String> getGeoId() {
        return geoId;
    }

    public void setGeoId(Map<Integer, String> geoId) {
        this.geoId = geoId;
    }

    public Map<Integer, String> getaLandM() {
        return aLandM;
    }

    public void setaLandM(Map<Integer, String> aLandM) {
        this.aLandM = aLandM;
    }

    public Map<Integer, String> getaWaterM() {
        return aWaterM;
    }

    public void setaWaterM(Map<Integer, String> aWaterM) {
        this.aWaterM = aWaterM;
    }

    public Map<Integer, String> getInterpLat() {
        return interpLat;
    }

    public void setInterpLat(Map<Integer, String> interpLat) {
        this.interpLat = interpLat;
    }

    public Map<Integer, String> getInterpLong() {
        return interpLong;
    }

    public void setInterpLong(Map<Integer, String> interpLong) {
        this.interpLong = interpLong;
    }
}
