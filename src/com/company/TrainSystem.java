package com.company;


import java.util.*;

public class TrainSystem {

    private String name;

    private List<Station> stations;


    public TrainSystem(String name) {
        this.name = name;

        //doubly-linked list
        this.stations = new LinkedList<>();

        //starting stations created
        Station capetown = new Station("capetown");
        Station bellville = new Station("bellville");
        Station durbanville = new Station("durbanville");
        Station paarl = new Station("paarl");
        Station wellington = new Station("wellington");

        this.stations.add(capetown);
        this.stations.add(bellville);
        this.stations.add(durbanville);
        this.stations.add(paarl);
        this.stations.add(wellington);

    }


    //adds a new station (built at end of track)
    public void addStation(String stationName) {
        Station newStation = new Station(stationName);
        this.stations.add(newStation);
    }

    public void viewAllStations() {
        System.out.println("------------------");
        System.out.println("All stations: ");
        for (Station station : this.stations) {
            System.out.println("-->" + station.getName());
        }
        System.out.println("------------------");
    }

    public String getName() {
        return name;
    }

    public List<Station> getStations() {
        return stations;
    }

    /**
     * Calculate the number of between Start and Stop stations
     *
     * @return
     */

    public List<Station> getStationObjects(String startStationName, String exitStationName) {

        List<Station> startExitStations = new ArrayList<>();

        for (int i = 0; i < getStations().size(); i++) {
            if (getStations().get(i).getName().equals(startStationName)) {
                startExitStations.add(getStations().get(i));
            }
            if (getStations().get(i).getName().equals(exitStationName)) {
                startExitStations.add(getStations().get(i));
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
        ListIterator listIterator = this.stations.listIterator();

        //only starts incrementing when the train reaches the start Station
        int countStops = 0;
        //when train arrives at starting Station
        boolean beginStopCounts = false;
        boolean exitStationArrival = false;

        while ((listIterator.hasNext())) {
            Station temp = this.stations.get(listIterator.nextIndex());
            if (beginStopCounts) {
                countStops += 1;
            }
            if (temp.getName().equals(startStation.getName())) {
                System.out.println("found the start station!!!!");
                beginStopCounts = true;
            }
            if(temp.getName().equals(exitStation.getName()) && (beginStopCounts)){
                System.out.println("arrived at the end station!!!");
                exitStationArrival = true;
                break;
            }
            listIterator.next();
        }
        if(!exitStationArrival) {
            while (listIterator.hasPrevious() && (!exitStationArrival)) {

                Station temp = this.stations.get(listIterator.previousIndex());
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

    public double calculateCost(int stopCount){

        //R5 per stop
        return stopCount*5;
    }




    public void llTest() {
        ListIterator iterator = stations.listIterator();

        while (iterator.hasNext()) {
            System.out.println(iterator);
        }
    }

}


