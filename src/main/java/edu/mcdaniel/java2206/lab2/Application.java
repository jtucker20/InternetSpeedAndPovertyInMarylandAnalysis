package edu.mcdaniel.java2206.lab2;

import edu.mcdaniel.java2206.lab2.fords.FordF150;
import edu.mcdaniel.java2206.lab2.fords.FordFocus;
import edu.mcdaniel.java2206.lab2.interfaces.Vehicle;
import edu.mcdaniel.java2206.lab2.toyotas.ToyotaCamry;
import edu.mcdaniel.java2206.lab2.toyotas.ToyotaCorolla;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is designed to start/wrap your class.
 */
@SpringBootApplication
public class Application {

    //=============================================================================================
    // Private Assets
    //=============================================================================================

    /**
     * The logging mechanism of the class.
     */
    private Logger log;


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
        ToyotaCamry camry = new ToyotaCamry();
        ToyotaCorolla corolla = new ToyotaCorolla();

        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(camry);
        vehicleList.add(corolla);

        for(Vehicle vehicle : vehicleList){

            if(vehicle instanceof ToyotaCorolla){
                ToyotaCorolla thisToyotaCorolla = (ToyotaCorolla) vehicle;
                thisToyotaCorolla.corollaLightsDim(0.0);
            }
        }

        Vehicle veh1 = new ToyotaCamry();
        Vehicle veh2 = new ToyotaCorolla();

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(veh1);
        vehicles.add(veh2);

        for(Vehicle vehicle : vehicles){
            vehicle.turnOnLights();
        }

        for(Vehicle vehicle : vehicles){
            System.out.println(vehicle.showLightsStatus());
        }

        System.out.println("Predefined behavior ends.");
        //===// Do not modify, please //=========================================================//
        //Here I'm making the Ford Vehicles
        Vehicle veh3 = new FordFocus();
        Vehicle veh4 = new FordF150();

        //Adding these to the list
        vehicles.add(veh3);
        vehicles.add(veh4);

        //Doing some actions on the vehicles.
        for(Vehicle vehicle : vehicles){
            vehicle.accel(50);
            vehicle.toggleLights();
            vehicle.brake(75);
        }

        //Printing out the state
        for(Vehicle vehicle : vehicles){
            System.out.println(vehicle.showVehicleState());
        }
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
