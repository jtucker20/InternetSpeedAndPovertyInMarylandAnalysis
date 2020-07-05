package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.exceptions;

public class CensusGeographicDataLoaderException extends RuntimeException {

    public CensusGeographicDataLoaderException(String msg){
        super(msg);
    }

    public CensusGeographicDataLoaderException(Exception e){
        super(e);
    }

    public CensusGeographicDataLoaderException(String msg, Exception e){
        super(msg, e);
    }
}
