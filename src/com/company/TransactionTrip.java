package com.company;

import java.util.Date;

public class TransactionTrip {

    private int amountStops;

    private String stationStart;

    private String stationEnd;

    private Date timestamp;

    private String memo;

    private Passenger passengerAccount;

    public TransactionTrip(String stationStart, String stationEnd, int amountStops, Passenger passengerAccount) {
        this.stationStart = stationStart;
        this.stationEnd = stationEnd;
        this.passengerAccount = passengerAccount;
        this.amountStops = amountStops;
        this.timestamp = new Date();


        this.memo = getTrip();
    }

    public String getMemo() {
        return memo;
    }

    public String getTrip(){
        return String.format(passengerAccount.getName() + " took [" + amountStops + "] stops from " + stationStart + "->" + stationEnd +
                " at (" + this.timestamp + ")");
    }
}
