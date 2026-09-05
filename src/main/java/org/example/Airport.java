package org.example;

import java.sql.Time;
import java.time.*;
import java.util.HashMap;
import java.util.Random;

public class Airport {
    private static int airportCount = 0;
    private String id;
    private ZoneId continentCity; //Asia/Tokio
    private HashMap<String, Flight> flightOut = new HashMap<>();

    public Airport(ZoneId continentCity){
        this.continentCity = continentCity;
        this.id = continentCity.getId().split("/")[1] + airportCount;
        airportCount++;
    }

    public String getId() {return id;}
    public String getCiti(){return continentCity.getId().split("/")[1];}

    public void createFlight(String placeOut, String placein, Time timeOut, Time timeIn) {
    }
}
