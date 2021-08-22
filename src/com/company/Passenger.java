package com.company;

import java.awt.font.TransformAttribute;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Passenger {

    private String name;

    private byte passwordHash[];//hashed password

    private String password;

    //unique ID for the user
    private String uuid;

    private double funds;

    private List<TransactionTrip> travelHistory;


    public Passenger(String id, String name, String password) {
        this.name = name;
        this.password = password;
        this.travelHistory = new ArrayList<>();

        //temp enable bottom later
        this.uuid = id;

        //Hash the password
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.passwordHash = md.digest(password.getBytes());

        } catch (NoSuchAlgorithmException e) {
            System.err.println("error NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Check whether the entered password is valid or not
     *
     * @return
     */
    public boolean validatePassword(String passwordToBeValidated) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(passwordToBeValidated.getBytes()), this.passwordHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void addNewTransactionTrip(String stationStart, String stationExit, int amountStops, double costOfTrip) {


        //create TransactionTrip object
        TransactionTrip newTransactionTrip = new TransactionTrip(stationStart, stationExit, amountStops, costOfTrip, this);


        //add object to the travelHistory List
        travelHistory.add(newTransactionTrip);

    }

    public void viewTravelHistory() {

        if (this.travelHistory.isEmpty()) {
            System.out.println("No travel history found");
            return;
        }

        for (int i = this.travelHistory.size() - 1; i >= 0; i--) {
            System.out.println(this.getTravelHistory().get(i).getMemo());
        }


    }

    public void addFunds(double funds) {
        if (funds > 0) {
            this.funds += funds;
        }
    }

    public void removeFunds(double costOfTrip) {
        this.funds -= costOfTrip;
    }

    public String getUuid() {
        return uuid;
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
