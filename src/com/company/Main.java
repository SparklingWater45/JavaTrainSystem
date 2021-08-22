package com.company;

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
                    //remove this
                    break;
                case 2:
                    //enter starting station
                    //enter exiting station
                    String startStation = "bellville";
                    String exitStation = "capetown";
                    handleTravelScreen(trainSystem);

                    //check passenger has enough funds for the trip


                    travelStation(trainSystem, startStation, exitStation, loggedInPassenger);

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
                    loggedInPassenger.viewTravelHistory();
                    break;
                case 5:
                    //quit
                    System.exit(0);
            }
        }
    }

    /**
     * Options for different stations
     */
    public static void handleTravelScreen(TrainSystem trainSystem) {


        String strStartingStation;
        String strExitStation;
        int intStartStation;
        int intEndStation;

        while (true) {
            System.out.println("List of all stations:");

            for (int i = 0; i < trainSystem.getListStations().size(); i++) {
                System.out.println("\t" + (i + 1) + "." + trainSystem.getListStations().get(i).getName());
            }

            System.out.print("Starting station number -> ");
            strStartingStation = sc.nextLine();

            try {
                intStartStation = Integer.parseInt(strStartingStation);

                if (intStartStation > trainSystem.getListStations().size() + 1) {
                    System.out.println("Invalid starting station specified");
                }
                System.out.print("Exiting station number -> ");
                strExitStation = sc.nextLine();

                intEndStation = Integer.parseInt(strExitStation);
                if (intEndStation > trainSystem.getListStations().size() + 1) {
                    System.out.println("Invalid exiting station specified");
                } else {
                    //break out while loop
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid value entered for station numbers");
            }
        }

        //now have the starting station index and exiting station index
        strStartingStation = trainSystem.getListStations().get(intStartStation).getName();
        strExitStation = trainSystem.getListStations().get(intEndStation).getName();
        travelStation(trainSystem, strStartingStation, strExitStation, loggedInPassenger);

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


        if (choice == 1) {
            /*
             * LOGIN
             * Get user login details
            sets the system loggedInUser as the matching user object in the TrainSystem passenger list */
            loggedInPassenger = loginUser(trainSystem);

        } else {

            createAccount(trainSystem);
        }
        /**
         * Get user details for creating an account
         */
//        createAccount();

        return true;
    }


    public static void createAccount(TrainSystem trainSystem) {

        boolean passedName = false;
        String name = "";

        //loops until valid name entered.
        while (!passedName) {
            System.out.print("Enter your name -> ");
            name = sc.nextLine();

            //validates all characters are alphabetical letters
            if (name.matches("[A-Za-z]+")) {
                passedName = true;
            }
        }
        System.out.print("Enter password -> ");
        String password = sc.nextLine();

        //sets the created account Passenger object to the loggedInUser.
        loggedInPassenger = trainSystem.createNewPassenger(name, password);
    }

    /**
     * Asks for user details -> sets loggedInUser to returned passenger object
     *
     * @param trainSystem
     * @return passenger object that matches credentials
     */
    public static Passenger loginUser(TrainSystem trainSystem) {
        String id;
        String password;
        Passenger foundPassenger = null;
        //get details
        //keep asking until they get it right
        while (true) {
            System.out.println("Enter id -> ");
            id = sc.nextLine();

            System.out.println("Enter password -> ");
            password = sc.nextLine();

            foundPassenger = trainSystem.validatePassengerCredentials(id, password);

            if (foundPassenger == null) {
                System.out.println("Invalid id/password combination entered , please try again");
            }
            if (foundPassenger != null) {
                break;
            }
        }
        System.out.println("User found -> " + foundPassenger.getName());
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


    public static void travelStation(TrainSystem trainSystem, String startStation, String exitStation, Passenger loggedInPassenger) {

        if (loggedInPassenger.getFunds() <= 0) {
            System.out.println("Invalid funds for the trip , add funds");
            return;
        }

        int amountStops = trainSystem.calculateStops(trainSystem.getStationObjects(startStation, exitStation));
        double costOfTrip = trainSystem.calculateCost(amountStops);

        if (loggedInPassenger.getFunds() < costOfTrip) {
            System.out.println("Invalid funds for the trip , add funds");
            return;
        }

        System.out.println("Valid funds for the trip");
        loggedInPassenger.removeFunds(costOfTrip);


        //create transaction trip object for passenger object
        loggedInPassenger.addNewTransactionTrip(startStation, exitStation, amountStops);

    }
}
