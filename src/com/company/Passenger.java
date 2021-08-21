package com.company;

import java.awt.font.TransformAttribute;
import java.util.ArrayList;
import java.util.List;

public class Passenger {

    private String name;

    private String password;

    private double funds;

    private List<TransactionTrip> travelHistory;


    public Passenger(String name, String password) {
        this.name = name;
        this.password = password;
        this.travelHistory = new ArrayList<>();
    }

    public void addNewTransactionTrip(String stationStart, String stationExit, int amountStops) {

        //create TransactionTrip object
        TransactionTrip newTransactionTrip = new TransactionTrip(stationStart, stationExit, amountStops, this);

        //add object to the travelHistory List
        travelHistory.add(newTransactionTrip);

        for(int i = 0; i < getTravelHistory().size() ; i++){
            System.out.println(getTravelHistory().get(i).getMemo());
        }

    }

    public void viewTravelHistory(){

        for(int i = this.travelHistory.size() ; i>0; i--){
            System.out.println(this.getTravelHistory().get(i));
        }

    }

    public void addFunds(double funds) {

        if (funds > 0) {
            this.funds += funds;
        }
    }

    public double getFunds() {
        return funds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TransactionTrip> getTravelHistory() {
        return travelHistory;
    }
}
