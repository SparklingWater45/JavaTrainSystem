package com.company;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Passenger {

    private String name;

    //hashed password
    private byte[] passwordHash;

    //unique ID for the user
    private final String travelCardUUID;

    private double funds;

    private List<TransactionTrip> travelHistory;

    public Passenger(String id, String name, String password) {
        this.name = name;
//        this.password = password;
        this.travelHistory = new ArrayList<>();

        //temp enable bottom later
        this.travelCardUUID = id;

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

    public String getTravelCardUUID() {
        return travelCardUUID;
    }

    public double getFunds() {
        return funds;
    }

    public String getName() {
        return name;
    }

    public List<TransactionTrip> getTravelHistory() {
        return travelHistory;
    }
}
