package com.company;

import java.util.Date;

public class TransactionTrip {

    private int amountStops;

    private String stationStart;

    private String stationEnd;

    private Date timestamp;

    private String memo;

    private Passenger passengerAccount;

    private double costOfTrip;

    public TransactionTrip(String stationStart, String stationEnd, int amountStops, double cost, Passenger passengerAccount) {
        this.stationStart = stationStart;
        this.stationEnd = stationEnd;
        this.passengerAccount = passengerAccount;
        this.amountStops = amountStops;
        this.timestamp = new Date();
        this.costOfTrip = cost;

        this.memo = getTrip();
        System.out.println(this.memo);


    }

    public String getMemo() {
        return memo;
    }

    public String getTrip() {

        return passengerAccount.getName() + " took [" + amountStops + "] stops for R" + costOfTrip + " from " + stationStart + "->" + stationEnd +
                " at (" + this.timestamp + ")";
    }
}
