package edu.mcdaniel.jtucker.internetspeedandpovertyinmarylandanalysis.maryland;

public class Record {

    private String max;
    private String min;
    private String avg;
    private String count;
    private String objectId;
    private String censusTract;

    public Record(String max, String min, String avg, String count, String objectId, String censusTract){
        this.max = max;
        this.min = min;
        this.avg = avg;
        this.count = count;
        this.objectId = objectId;
        this.censusTract = censusTract;
    }

    @Override
    public String toString() {
        return "Object ID: " + objectId + " Max: " + max + " Min: " + min + " Avg: " + avg + " Count: " + count
                + " Census Tract: " + censusTract;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCensusTract() {
        return censusTract;
    }

    public void setCensusTract(String censusTract) {
        this.censusTract = censusTract;
    }
}
