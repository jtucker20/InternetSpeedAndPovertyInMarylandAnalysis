package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.exceptions;

public class CensusPovertyDataLoaderException extends RuntimeException {

    public CensusPovertyDataLoaderException(String msg){
        super(msg);
    }

    public CensusPovertyDataLoaderException(Exception e){
        super(e);
    }

    public CensusPovertyDataLoaderException(String msg, Exception e){
        super(msg, e);
    }
}
