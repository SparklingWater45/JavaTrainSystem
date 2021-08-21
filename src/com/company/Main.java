package com.company;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    private static Passenger loggedInPassenger;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        TrainSystem trainSystem = new TrainSystem("London Tube");

        Passenger passengerDylan = new Passenger("1", "Dylan", "1");

//        loggedInPassenger = passengerDylan;

        //add funds(amount)
        //add funds to customer


        //travel(startStation,endStation)
        //      -> calculates amount of stops -> end of  track turn around -> 1` stop = R5

        //      -> calculates if enough funds
        //      ->if valid transaction processes -> arrive at new station

        int optionChoice = 0;
        boolean startScreenPassed = false;

        while (true) {

            //stops handleStartScreen() running on every loop
            if (!startScreenPassed) {
                do {
                    startScreenPassed = handleStartScreen(trainSystem);
                } while (!startScreenPassed);
            }

            do {
                optionChoice = handleMenuOptions();
            } while (optionChoice == 0);

            switch (optionChoice) {
                case 1:
                    //loggedInPassenger
                    //set logged in passenger global variable to the chosen passenger

                    break;
                case 2:
                    //enter starting station
                    //enter exiting station
                    String startStation = "bellville";
                    String exitStation = "capetown";
                    travelStation(trainSystem, startStation, exitStation);

//                    int stops = trainSystem.calculateStops(trainSystem.getStationObjects("bellville","capetown"));
//                    trainSystem.calculateCost(stops);
                    //determine amount of stops to chosen station
                    //determine cost (amount of stops*R5)

                    break;
                case 3:
                    //add funds to account
                    addPassengerFunds(passengerDylan);
                    break;
                case 4:
                    //view travel historsy + costs

                case 5:
                    //quit
                    System.exit(0);
            }
        }

    }

    public static boolean handleStartScreen(TrainSystem trainSystem) {

        int choice = 0;

        do {
            System.out.println("""
                    1.Login
                    2.Create Account
                    """);
            System.out.print("Menu choice -> ");
            try {
                String menuChoice = sc.nextLine();
                choice = Integer.parseInt(menuChoice);

            } catch (Exception e) {
                System.out.println("Error occurred");
                e.printStackTrace();
            }
        }
        while ((choice != 1) && (choice != 2));


        if(choice == 1) {
            /*
             * LOGIN
             * Get user login details
            sets the system loggedInUser as the matching user object in the TrainSystem passenger list */
            loggedInPassenger = loginUser(trainSystem);

        }

        /**
         * Get user details for creating an account
     */
//        createAccount();

        return false;
    }


    public Passenger createAccount() {

        return null;
    }

    public static Passenger loginUser(TrainSystem trainSystem) {
        //search for user id
        //check if id given = password
        //return user object from TrainSystem
        //set user object as global loggedInUser variable

        String id;
        String password;
        Passenger foundPassenger = null;

        //get details
        //keep asking until they get it right
        do {
            System.out.println("Enter id -> ");
            id = sc.nextLine();

            System.out.println("Enter password -> ");
            password = sc.nextLine();

           foundPassenger = trainSystem.validatePassengerCredentials(id,password);

            if (foundPassenger == null) {
                System.out.println("Invalid id/password combination entered , please try again");
            }
        } while (foundPassenger != null);


        return foundPassenger;

    }


    public static int handleMenuOptions() {
        System.out.println("""
                1.Login
                2.Travel on tube
                3.Add funds
                4.View Travel Report
                5.Quit
                """);

        System.out.print("->");
        try {
            String optionChoice = sc.nextLine();
            int menuChoice = Integer.parseInt(optionChoice);
            if ((menuChoice <= 0) || (menuChoice > 5)) {
                System.out.println("Invalid option entered , please try again");
            } else {
                //valid option choice returned
                return Integer.parseInt(optionChoice);
            }
        } catch (Exception e) {
            System.out.println("Entered value must be a number");
            return 0;
        }

        return 0;
    }


    public static void addPassengerFunds(Passenger loggedInPassenger) {

        System.out.print("Enter fund amount -> ");
        String amount = sc.nextLine();

        if (Integer.parseInt(amount) > 0) {
            loggedInPassenger.addFunds(Integer.parseInt(amount));
            System.out.println("Funds added");
            System.out.println("New balance : " + loggedInPassenger.getFunds());
        } else {
            System.out.println("Invalid funds entered");
        }
    }


    public static void travelStation(TrainSystem trainSystem, String startStation, String exitStation) {
        int amountStops = trainSystem.calculateStops(trainSystem.getStationObjects(startStation, exitStation));
        double cost = trainSystem.calculateCost(amountStops);

        //create transaction trip object for passenger object
        loggedInPassenger.addNewTransactionTrip(startStation, exitStation, amountStops);


//        loggedInPassenger.viewTravelHistory();

//        for (int i = 0; i < loggedInPassenger.getTravelHistory().size(); i++) {
//            System.out.println(loggedInPassenger.getTravelHistory().get(i).getMemo());
//        }

    }
}
