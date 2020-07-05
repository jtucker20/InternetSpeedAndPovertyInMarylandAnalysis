package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.exceptions;

public class InternetSpeedDataLoaderException extends RuntimeException {

    public InternetSpeedDataLoaderException(String msg){
        super(msg);
    }

    public InternetSpeedDataLoaderException(Exception e){
        super(e);
    }

    public InternetSpeedDataLoaderException(String msg, Exception e){
        super(msg, e);
    }
}