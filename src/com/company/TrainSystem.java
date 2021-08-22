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
        return new Passenger(newId, name, password);
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
                if (uuid.compareTo(p.getUuid()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique); //while nonUnique is true

        System.out.println("new id -> " + uuid);
        return uuid;
    }

    public Passenger validatePassengerCredentials(String id, String password) {

        for (Passenger p : this.listPassengers) {
            //found id -> user exists

            if (p.getUuid().equals(id)) {
                //check if password matches entered string
                if (p.getPassword().equals(password)) {
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

    //return both objects relating to the string name passed
    public List<Station> getStationObjects(String startStationName, String exitStationName) {

        List<Station> startExitStations = new ArrayList<>();
        for (int i = 0; i < getListStations().size(); i++) {
            if (getListStations().get(i).getName().equals(startStationName)) {
                startExitStations.add(getListStations().get(i));
            }
            if (getListStations().get(i).getName().equals(exitStationName)) {
                startExitStations.add(getListStations().get(i));
            }
            if (startExitStations.size() == 2) {
                //returns list of station
                //[0] -start station
                //[1] - end station
                return startExitStations;
            }
        }
        //couldn't find both object names
        return null;
    }

    public int calculateStops(List<Station> startExitStations) {

        Station startStation = startExitStations.get(0);
        Station exitStation = startExitStations.get(1);
        ListIterator<Station> listIterator = this.listStations.listIterator();

        //only starts incrementing when the train reaches the start Station
        int countStops = 0;
        //when train arrives at starting Station
        boolean beginStopCounts = false;
        boolean exitStationArrival = false;

        while ((listIterator.hasNext())) {
            Station temp = this.listStations.get(listIterator.nextIndex());
            if (beginStopCounts) {
                countStops += 1;
            }
            if (temp.getName().equals(startStation.getName())) {
                beginStopCounts = true;
            }
            if (temp.getName().equals(exitStation.getName()) && (beginStopCounts)) {
                exitStationArrival = true;
                break;
            }
            listIterator.next();
        }
        if (!exitStationArrival) {
            while (listIterator.hasPrevious() && (!exitStationArrival)) {

                Station temp = this.listStations.get(listIterator.previousIndex());
                //starts searching at index 0
                //cant get to end of track without finding start station
                countStops += 1;
                if (temp.getName().equals(exitStation.getName())) {
                    //arrived at last station
                    exitStationArrival = true;
                }
                listIterator.previous();
            }
        }
        //return number of stops
        System.out.println(countStops);
        return countStops;
    }

    public double calculateCost(int stopCount) {
        //R5 per stop
        return stopCount * 5;
    }

    public String getName() {
        return name;
    }

    public List<Station> getListStations() {
        return listStations;
    }

    public List<Passenger> getListPassengers() {
        return listPassengers;
    }


}


