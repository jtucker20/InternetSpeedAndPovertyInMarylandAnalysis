package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography;

import java.util.ArrayList;
import java.util.List;

public class CensusGeographicDataTractFilter {

    private List<Tract> listOfTracts;
    private List<Tract> listOfCarrollTracts;

    private static final String CARROLL_COUNTY_CENSUS_TRACT_PREFIX = "24013";

    public CensusGeographicDataTractFilter(List<Tract> listOfTracts){
        this.listOfTracts = listOfTracts;
        this.listOfCarrollTracts = new ArrayList<>();
    }


    public void filterTracts() {
        listOfTracts.forEach(tract -> {
            if(tract.getGeoIdStr().contains(CARROLL_COUNTY_CENSUS_TRACT_PREFIX)){
                this.listOfCarrollTracts.add(tract);
            }
        });
    }


    public List<Tract> getListOfCarrollTracts() {
        return listOfCarrollTracts;
    }
}
