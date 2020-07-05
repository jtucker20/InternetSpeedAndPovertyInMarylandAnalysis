package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.census.geography;

import java.text.NumberFormat;

public class Tract {

    private static final double R_EARTH = 6369497; // Earth Radius in Meters
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private final int tractNumber;
    private final String stateCode;
    private final String geoIdStr;
    private final long geoIdNum;
    private final long aLandM;
    private final long aWaterM;
    private final double lat;
    private final double lng;
    private final long totalArea;
    private final double westToEastLength;
    private final double northToSouthLength;

    private double dLat;
    private double dLong;

    private double leftTopLat;
    private double leftTopLng;

    private double leftBtmLat;
    private double leftBtmLng;

    private double rightTopLat;
    private double rightTopLng;

    private double rightBtmLat;
    private double rightBtmLng;

    /**
     * Constructor
     * @param tractNumber
     * @param stateCode
     * @param geoIdStr
     * @param geoIdNum
     * @param aLandM
     * @param aWaterM
     * @param lat
     * @param lng
     */
    public Tract(
            int tractNumber,
            String stateCode,
            String geoIdStr,
            long geoIdNum,
            long aLandM,
            long aWaterM,
            double lat,
            double lng
    ) {
        this.tractNumber = tractNumber;
        this.stateCode = stateCode;
        this.geoIdStr = geoIdStr;
        this.geoIdNum = geoIdNum;
        this.aLandM = aLandM;
        this.aWaterM = aWaterM;
        this.lat = lat * Math.PI / 180;
        this.lng = lng * Math.PI / 180;

        this.totalArea = this.aLandM + this.aWaterM;

        this.westToEastLength = Math.sqrt(this.totalArea);
        this.northToSouthLength = this.westToEastLength;

        numberFormat.setMinimumFractionDigits(14);
    }

    /**
     * Calculators
     */
    public void calculate(){

        this.leftTopLat = 180 * this.lat / Math.PI;
        this.leftTopLng = 180 * this.lng / Math.PI;

        // x change in long
        double numerator = Math.pow(Math.sin((this.westToEastLength / 2) / R_EARTH), 2);
        double denominator = Math.pow(Math.cos(this.lat), 2);
        double arg = Math.sqrt(numerator/denominator);
        this.dLong = 2 * Math.asin(arg);

        // y change in lat

        this.dLat = - this.northToSouthLength / R_EARTH;

        this.leftBtmLat = 180 * (this.lat + this.dLat) / Math.PI;
        this.leftBtmLng = 180 * this.lng / Math.PI;

        this.rightTopLat = 180 * this.lat / Math.PI;
        this.rightTopLng = 180 * (this.lng + this.dLong) / Math.PI;

        this.rightBtmLat = 180 * (this.lat + this.dLat) / Math.PI;
        this.rightBtmLng = 180 * (this.lng + this.dLong) / Math.PI;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("The ").append(this.tractNumber)
                .append(" tract has the following characteristics: ")
                .append("\nLatUL: ").append(numberFormat.format(this.leftTopLat))
                .append(" LngUL: ").append(numberFormat.format(this.leftTopLng))
                .append("\nLatLR: ").append(numberFormat.format(this.rightBtmLat))
                .append(" LngLR: ").append(numberFormat.format(this.rightBtmLng));
        return builder.toString();
    }

    public int getTractNumber() {
        return tractNumber;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getGeoIdStr() {
        return geoIdStr;
    }

    public long getGeoIdNum() {
        return geoIdNum;
    }

    public double getLeftTopLat() {
        return leftTopLat;
    }

    public double getLeftTopLng() {
        return leftTopLng;
    }

    public double getLeftBtmLat() {
        return leftBtmLat;
    }

    public double getLeftBtmLng() {
        return leftBtmLng;
    }

    public double getRightTopLat() {
        return rightTopLat;
    }

    public double getRightTopLng() {
        return rightTopLng;
    }

    public double getRightBtmLat() {
        return rightBtmLat;
    }

    public double getRightBtmLng() {
        return rightBtmLng;
    }
}
