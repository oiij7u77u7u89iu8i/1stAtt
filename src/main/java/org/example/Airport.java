package org.example;

import java.sql.Time;
import java.time.*;
import java.util.HashMap;
import java.util.Random;

public class Airport {
    private static int airportCount = 0;
    private int id;
    private ZoneId continentCity; //Asia/Tokio
    private HashMap<String, Flight> flightOut = new HashMap<>();

    public Airport(ZoneId continentCity){
        this.continentCity = continentCity;
        this.id = airportCount;
        airportCount++;
    }

    public ZoneId getZoneId() { return continentCity; }
    public int getId() {return id;}
    public String getCiti(){return continentCity.getId().split("/")[1];}

    public void createFlight(Airport placeOut, Airport placeIn, Time timeOut, Time timeIn, seats) {
        Flight current = new Flight(placeOut, placeIn, timeOut, timeIn, seats);
        flightOut.put(current.getId(), current);
    }
}
