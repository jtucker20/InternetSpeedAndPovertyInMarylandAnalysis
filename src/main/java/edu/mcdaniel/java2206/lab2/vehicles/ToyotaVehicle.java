package edu.mcdaniel.java2206.lab2.vehicles;

import edu.mcdaniel.java2206.lab2.car.CarBaseClass;

public abstract class ToyotaVehicle extends CarBaseClass {

    private double speed;

    public ToyotaVehicle(){
        super();
        setMake("Toyota");
        speed = 0;
    }

    public String displaySpeedAsString(){
        return "This Toyota's speed is: " + speed;
    }

    public abstract String getWreckReportForToyota();


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
