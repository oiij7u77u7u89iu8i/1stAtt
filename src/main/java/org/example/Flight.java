package org.example;


import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.example.ProjectClock.now;

public class Flight {
    private String id;
    private Instant timeOut;
    private Instant timeIn;
    private Airport placeOut;
    private Airport placeIn;
    private int seats;

    public Flight(Airport placeOut, Airport placeIn, Instant timeOut, Instant timeIn, int seats){
        this.id = placeOut.getCiti().toUpperCase().substring(0, 3) + placeIn.getCiti().toUpperCase().substring(0, 3) + timeIn;
        this.timeOut = timeOut;
        this.timeIn = timeIn;
        this.placeIn = placeIn;
        this.placeOut = placeOut;
        this.seats = seats;
    }


    public LocalDateTime getTimeIn(){return LocalDateTime.ofInstant(timeIn, placeIn.getZoneId());}
    public LocalDateTime getTimeOut(){return LocalDateTime.ofInstant(timeOut, placeOut.getZoneId());}
    public String getId() {return id;}
    public Duration getTimeInAir() {return Duration.between(getTimeOut(), getTimeIn());}
    public int getSeats() {return seats;}
    public Airport getPlaceOut(){return placeOut;}
    public Airport getPlaceIn(){return placeIn;}


    public void setTimeOut(Instant timOut){timeOut = timOut;}
    public void setTimeIn(Instant timIn){
        timeIn = timIn;
    }
}
