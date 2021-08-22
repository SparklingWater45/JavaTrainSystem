package com.company;


import java.util.*;

public class TrainSystem {

    private String name;

    //list of different stations on TrainSystem railway.
    private List<Station> listStations;

    //list of passengers
    private List<Passenger> listPassengers;

    public TrainSystem(String name) {
        this.name = name;

        //doubly-linked list
        this.listStations = new LinkedList<>();

        this.listPassengers = new ArrayList<>();

        //starting stations created
        Station capetown = new Station("capetown"); //0
        Station bellville = new Station("bellville"); //1
        Station durbanville = new Station("durbanville"); //2
        Station paarl = new Station("paarl"); // 3
        Station wellington = new Station("wellington"); //4

        this.listStations.add(capetown);
        this.listStations.add(bellville);
        this.listStations.add(durbanville);
        this.listStations.add(paarl);
        this.listStations.add(wellington);

        //existing passenger created
        Passenger dylan = new Passenger("1", "Dylan", "1");
        this.listPassengers.add(dylan);
    }

    /**
     * @param name
     * @param password
     * @return the newly created PassengerObject
     */
    public Passenger createNewPassenger(String name, String password) {
        String newId = createNewPassengerUUID();
        Passenger newPassenger = new Passenger(newId, name, password);
        this.listPassengers.add(newPassenger);
        return newPassenger;
    }

    private String createNewPassengerUUID() {
        //init
        String uuid;
        Random rng = new Random();
        int len = 5;
        boolean nonUnique;

        //continue looping until we get a unique ID
        do {
            //generate the number
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }
            //compare to existing users UUID to make sure it's unique
            nonUnique = false;
            for (Passenger p : this.listPassengers) {
                if (uuid.compareTo(p.getTravelCardUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique); //while nonUnique is true

        System.out.println("new created id -> " + uuid);
        return uuid;
    }

    public Passenger validatePassengerCredentials(String id, String password) {

        for (Passenger p : this.listPassengers) {
            //found id -> user exists

            if (p.getTravelCardUUID().equals(id)) {
                //check if password matches entered string
                if (p.validatePassword(password)) {
                    return p;
                }
            }
        }
        //invalid credentials / user doesn't exist
        return null;
    }

    //adds a new station (built at end of track)
    public void addStation(String stationName) {
        Station newStation = new Station(stationName);
        this.listStations.add(newStation);
    }

    public void viewAllStations() {
        System.out.println("------------------");
        System.out.println("All stations: ");
        for (Station station : this.listStations) {
            System.out.println("-->" + station.getName());
        }
        System.out.println("------------------");
    }

    //returns Station objects that match the station String name
    public List<Station> getStationObjects(String startStationName, String exitStationName) {

        int startStationIndex = -1;
        int exitStationIndex = -1;
        List<Station> startExitStations = new ArrayList<>();

        for (int i = 0; i < getListStations().size(); i++) {
            if (getListStations().get(i).getName().equals(startStationName)) {
                startStationIndex = i;
            }

            if (getListStations().get(i).getName().equals(exitStationName)) {
                exitStationIndex = i;
            }
        }
        if ((startStationIndex != -1) && (exitStationIndex != -1)) {
            //[0] -start station
            //[1] - end station
            startExitStations.add(getListStations().get(startStationIndex));
            startExitStations.add(getListStations().get(exitStationIndex));
            return startExitStations;
        }
        //couldn't find both object names
        return null;
    }

    public int calculateStops(List<Station> startExitStations) {

        Station startStation = startExitStations.get(0);
        Station exitStation = startExitStations.get(1);
        ListIterator<Station> listIterator = this.listStations.listIterator();

        //only starts incrementing when the train reaches the startStation
        int countStops = 0;

        //when train arrives at station -> true
        boolean beginStopCounts = false;
        boolean exitStationArrival = false;

        while ((listIterator.hasNext())) {
            Station temp = this.listStations.get(listIterator.nextIndex());

            System.out.println(temp.getName());

            if (beginStopCounts) {
                countStops += 1;
            }
            if (temp.getName().equals(startStation.getName())) {
                System.out.println("start station found");
                beginStopCounts = true;
            }
            if (temp.getName().equals(exitStation.getName()) && (beginStopCounts)) {
                System.out.println("exit station found");
                exitStationArrival = true;
                break;
            }
            listIterator.next();
        }

        if (!exitStationArrival) {
            //starts from end of track -> loops backwards
            while (listIterator.hasPrevious() && (!exitStationArrival)) {

                Station temp = this.listStations.get(listIterator.previousIndex());

                if (temp.getName().equals(exitStation.getName())) {
                    //arrived at last station
                    System.out.println("exit station arrived");
                    exitStationArrival = true;
                } else {
                    countStops += 1;
                }
                listIterator.previous();
            }
        }
        System.out.println(countStops);
        return countStops;
    }

    public double calculateCost(int stopCount) {
        //R5 per stop
        return stopCount * 5;
    }

    public List<Station> getListStations() {
        return listStations;
    }

    public List<Passenger> getListPassengers() {
        return listPassengers;
    }
}


