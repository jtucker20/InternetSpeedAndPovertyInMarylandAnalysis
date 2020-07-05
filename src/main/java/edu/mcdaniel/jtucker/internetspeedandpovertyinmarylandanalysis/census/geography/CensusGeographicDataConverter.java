package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CensusGeographicDataConverter {

//=============================================================================================
    // Private Assets
    //=============================================================================================

    /**
     * This provides access to logging.
     */
    private final Logger log = LogManager.getLogger(CensusGeographicDataConverter.class);

    /**
     * Field that will hold the values of State Codes
     */
    private final Map<Integer, String> state;

    /**
     * Field that will hold the values of GeoID for the particular geographic tract
     */
    private final Map<Integer, String> geoIdStr;

    /**
     * Field that will hold the values of GeoID for the particular geographic tract
     */
    private final Map<Integer, String> aLandMeterStr;

    /**
     * Field that will hold the values of GeoID for the particular geographic tract
     */
    private final Map<Integer, String> aWaterMeterStr;

    /**
     * Field that will hold the values of GeoID for the particular geographic tract
     */
    private final Map<Integer, String> interpLatStr;

    /**
     * Field that will hold the values of GeoID for the particular geographic tract
     */
    private final Map<Integer, String> interpLongStr;

    /**
     * Field that will hold the values of GeoID for the particular geographic tract
     */
    private Map<Integer, Long> geoIdNum;

    /**
     * Field that will hold the values of square meters of land
     */
    private Map<Integer, Long> aLandM;

    /**
     * Field that will hold the values of square meters of water
     */
    private Map<Integer, Long> aWaterM;

    /**
     * Field that will hold the values of Interpolated Latitude
     */
    private Map<Integer, Double> interpLat;

    /**
     * Field that will hold the values of Interpolated Longitude
     */
    private Map<Integer, Double> interpLong;

    private List<Tract> listOfTracts;

    /**
     * Field that will hold the Tract objects.
     */


    //=============================================================================================
    // Constructor(s)
    //=============================================================================================

    /**
     * This No Argument constructor Will use the internal file.
     */
    public CensusGeographicDataConverter(
            Map<Integer, String> state,
            Map<Integer, String> geoId,
            Map<Integer, String> aLandM,
            Map<Integer, String> aWaterM,
            Map<Integer, String> interpLat,
            Map<Integer, String> interpLong
    ) {
        this.state = state;
        this.geoIdStr = geoId;
        this.aLandMeterStr = aLandM;
        this.aWaterMeterStr = aWaterM;
        this.interpLatStr = interpLat;
        this.interpLongStr = interpLong;

        this.geoIdNum = new HashMap<>();
        this.aLandM = new HashMap<>();
        this.aWaterM = new HashMap<>();
        this.interpLat = new HashMap<>();
        this.interpLong = new HashMap<>();

        this.listOfTracts = new ArrayList<>();
    }


    //=============================================================================================
    // Methods
    //=============================================================================================

    /**
     * Converter
     */
    public void convertValues(){
        // state
        // Already in the correct Form


        // Convert data in geoIdStr to geoIdNum
        this.geoIdStr.forEach((i,s)->{
            long value = Long.parseLong(s);
            this.geoIdNum.put(i, value);
        });

        // Convert  aLandMeterStr to aLandM
        this.aLandMeterStr.forEach((i, s) -> {
            long value = Long.parseLong(s);
            this.aLandM.put(i, value);
        });

        // Convert aWaterMeterStr to aWaterM
        this.aWaterMeterStr.forEach((i, s) -> {
            long value = Long.parseLong(s);
            this.aWaterM.put(i, value);
        });

        // Conver interpLatStr to interpLat
        this.interpLatStr.forEach((i, s)->{
            double value = Double.parseDouble(s);
            this.interpLat.put(i, value);
        });

        // Convert interpLongStr to interpLong
        this.interpLongStr.forEach((i, s)->{
            double value = Double.parseDouble(s);
            this.interpLong.put(i, value);
        });

        log.info("All converted.");
    }


    /**
     * Record Maker
     */
    public void makeRecords(){

        this.state.forEach((i, s) -> {
            int tractNumber = i;

            String stateCode = s;

            String geoIdStr = this.geoIdStr.get(i);
            long geoIdNum = this.geoIdNum.get(i);

            long aLandM = this.aLandM.get(i);
            long aWaterM = this.aWaterM.get(i);

            double lat = this.interpLat.get(i);
            double lng = this.interpLong.get(i);

            Tract tract = new Tract(
                    tractNumber,
                    stateCode,
                    geoIdStr,
                    geoIdNum,
                    aLandM,
                    aWaterM,
                    lat,
                    lng
            );

            tract.calculate();


            this.listOfTracts.add(tract);

            log.info("Tract Of Interest: {}", tract);
        });
    }

    //=============================================================================================
    // Getters and Setters
    //=============================================================================================


    public Map<Integer, String> getState() {
        return state;
    }

    public Map<Integer, String> getGeoIdStr() {
        return geoIdStr;
    }

    public Map<Integer, Long> getGeoIdNum() {
        return geoIdNum;
    }

    public Map<Integer, Long> getaLandM() {
        return aLandM;
    }

    public Map<Integer, Long> getaWaterM() {
        return aWaterM;
    }

    public Map<Integer, Double> getInterpLat() {
        return interpLat;
    }

    public Map<Integer, Double> getInterpLong() {
        return interpLong;
    }

    public List<Tract> getListOfTracts() {
        return listOfTracts;
    }
}
