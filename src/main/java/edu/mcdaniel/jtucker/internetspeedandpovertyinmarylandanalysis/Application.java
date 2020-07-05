package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis;

import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography.CensusGeographicDataConverter;
import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography.CensusGeographicDataLoader;
import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography.CensusGeographicDataTractFilter;
import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography.Tract;
import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.poverty.CensusPovertyDataLoader;
import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.maryland.InternetSpeedDataLoader;
import edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.maryland.Record;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is designed to start/wrap your class.
 */
@SpringBootApplication
public class Application {

    //=============================================================================================
    // Private Assets
    //=============================================================================================


    //=============================================================================================
    // Constructor(s)
    //=============================================================================================

    /**
     * The constructor for the Spring Boot application
     */
    public Application(){
        //This constructor is empty as no additional information need be provided.
        //This is an implemented No Argument Constructor.
    }


    //=============================================================================================
    // Major Methods
    //=============================================================================================

    //No major methods to implement


    //=============================================================================================
    // PSVM
    //=============================================================================================

    /**
     * This method actually accomplishes the running of the code we are seeking to write
     * @param args the input from the command line.
     */
    public static void main(String[] args) {

        //===// Spring Application Hook //=======================================================//
        SpringApplication.run(Application.class, args);

        //Please do not change this information.
        CensusGeographicDataLoader censusLoader = new CensusGeographicDataLoader();
        censusLoader.readInFile();

        CensusGeographicDataConverter converter = new CensusGeographicDataConverter(
                censusLoader.getState(),
                censusLoader.getGeoId(),
                censusLoader.getaLandM(),
                censusLoader.getaWaterM(),
                censusLoader.getInterpLat(),
                censusLoader.getInterpLong()
        );
        converter.convertValues();
        converter.makeRecords();

        CensusGeographicDataTractFilter filter = new CensusGeographicDataTractFilter(converter.getListOfTracts());
        filter.filterTracts();

        List<Tract> carrollCountyTracts = filter.getListOfCarrollTracts();

        CensusPovertyDataLoader povertyDataLoader = new CensusPovertyDataLoader();
        povertyDataLoader.readInFile();

        Map<String, String> povertyRate = new HashMap<>();

        carrollCountyTracts.forEach(tract -> povertyRate.put(tract.getGeoIdStr(), povertyDataLoader.getPovertyRateForTract(tract)));

        InternetSpeedDataLoader speedDataLoader = new InternetSpeedDataLoader();
        speedDataLoader.readInFile();
        speedDataLoader.collate(carrollCountyTracts);

        Map<String, Record> internetSpeedByTract = new HashMap<>();

        carrollCountyTracts.forEach(tract -> internetSpeedByTract.put(tract.getGeoIdStr(), speedDataLoader.getSpeedForTract(tract)));

        System.out.print("|GEOID         |POVERTY RATE  | MAX DL SPEED | MIN DL SPEED | AVG DL SPEED |\n");

        carrollCountyTracts.forEach(tract -> {
            String geoID = tract.getGeoIdStr();
            String povRate = povertyRate.get(geoID);
            Record speedRecord = internetSpeedByTract.get(geoID);

            System.out.print("|" + geoID);
            System.out.print("  |" + povRate);
            System.out.print(" |" + speedRecord.getMax());
            System.out.print(" |" + speedRecord.getMin());
            System.out.print(" |" + speedRecord.getAvg() + " |" );
            System.out.print("\n");

        });
    }


    //=============================================================================================
    // Minor Methods(s)
    //=============================================================================================

    //No minor methods made for this class


    //=============================================================================================
    // Getters and Setters
    //=============================================================================================

    //No private assets we want to expose in this class

}
