package com.company;

import java.util.Scanner;

public class Main {

    private static Passenger loggedInPassenger;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        TrainSystem trainSystem = new TrainSystem("London Tube");
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
                    //travel on the tube system
                    handleTravelScreen(trainSystem);
                    break;
                case 2:
                    //view passengers current balance
                    viewCurrentBalance();
                    break;
                case 3:
                    //add funds to account
                    addPassengerFunds(loggedInPassenger);
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

                //gets index of startStation from TrainSystem listStations
                intStartStation = Integer.parseInt(strStartingStation) - 1;

                if (intStartStation > trainSystem.getListStations().size() - 1) {
                    System.out.println("Invalid starting station specified");
                }
                System.out.print("Exiting station number -> ");
                strExitStation = sc.nextLine();

                //gets index of exitStation from TrainSystem listStations
                intEndStation = Integer.parseInt(strExitStation) - 1;

                if (intEndStation > trainSystem.getListStations().size() - 1) {
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
        travelStation(trainSystem, strStartingStation, strExitStation);

    }

    public static void travelStation(TrainSystem trainSystem, String startStation, String exitStation) {

        //minimum trip cost = R5
        if (loggedInPassenger.getFunds() <= 0) {
            System.out.println("Invalid funds for the trip , add funds");
            return;
        }
        //amount stops required to perform trip
        int amountStops = trainSystem.calculateStops(trainSystem.getStationObjects(startStation, exitStation));

        //costOfTrip =  5 * amountStops
        double costOfTrip = trainSystem.calculateCost(amountStops);
        System.out.println("R" + costOfTrip + " = cost of trip");

        //if passenger has insufficient funds
        if (loggedInPassenger.getFunds() < costOfTrip) {
            System.out.println("Invalid funds for the trip , add funds");
            return;
        }
        //remove costOfTrip from passengers current funds
        loggedInPassenger.removeFunds(costOfTrip);
        //create transaction trip object for passenger object
        loggedInPassenger.addNewTransactionTrip(startStation, exitStation, amountStops, costOfTrip);
    }



    public static void viewCurrentBalance(){
        System.out.println("Current balance : R" + loggedInPassenger.getFunds());
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
            //Get passenger details for login
            loggedInPassenger = loginUser(trainSystem);
        } else {
            //Get passenger details for creating an account
            createAccount(trainSystem);
        }
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
            System.out.print("Enter id -> ");
            id = sc.nextLine();

            System.out.print("Enter password -> ");
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
        System.out.println();
        System.out.println("-----------------------");
        System.out.println();
        System.out.println("""
                1.Travel On Tube
                2.View Current Balance
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

        if (Double.parseDouble(amount) > 0) {
            loggedInPassenger.addFunds(Double.parseDouble(amount));
            System.out.println("Funds added");
            System.out.println("New balance : " + loggedInPassenger.getFunds());
        } else {
            System.out.println("Invalid funds entered");
        }

    }


}
