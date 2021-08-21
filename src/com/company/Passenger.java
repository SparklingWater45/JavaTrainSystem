package com.company;

import java.util.List;

public class Passenger {

    private String name;

    private String password;

    private double funds;

    private List<TransactionTrip> travelHistory;


    public Passenger(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void addFunds(double funds){

        if(funds >0){
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

}
