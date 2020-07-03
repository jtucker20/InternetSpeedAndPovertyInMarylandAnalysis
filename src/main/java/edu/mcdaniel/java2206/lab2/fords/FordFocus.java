package edu.mcdaniel.java2206.lab2.fords;

import edu.mcdaniel.java2206.lab2.interfaces.Vehicle;
import edu.mcdaniel.java2206.lab2.vehicles.FordVehicle;

public class FordFocus extends FordVehicle implements Vehicle {
    @Override
    public String wreckReport() {
        return null;
    }

    @Override
    public void accel(double percentOfMaxAccel) {

    }

    @Override
    public void brake(double percentOfMaxBrake) {

    }

    @Override
    public void turnOnLights() {

    }

    @Override
    public void toggleLights() {

    }

    @Override
    public String showLightsStatus() {
        return null;
    }

    @Override
    public String showVehicleState() {
        return null;
    }
}
