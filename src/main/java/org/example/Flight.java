package org.example;


import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.example.ProjectClock.now;

public class Flight {
    private String id;
    private Instant timeOut;
    private Instant timeIn;
    private Airport placeOut;
    private Airport placeIn;
    private Seats count;

    public class Seats{
        private int firstClass;
        private int ecoClass;



        public Seats(int firstClass, int ecoClass){
            this.ecoClass = ecoClass;
            this.firstClass = firstClass;
        }

        public int getFirstClass() {
            return firstClass;
        }

        public int getEcoClass() {
            return ecoClass;
        }

        public void setEcoClass(int ecoClass) throws Exception{
            if(now().isAfter(getTimeOut())){
                throw new Exception("u r late nigga");
            }
            this.ecoClass = ecoClass;
        }

        public void setFirstClass(int firstClass) throws Exception{
            if(now().isAfter(getTimeOut())){
                throw new Exception("u r late nigga");
            }
            this.firstClass = firstClass;
        }
        public void sellFirstClassTickets(int count) throws Exception{
            if(count > getFirstClass() || count <= 0){
                throw new Exception("Sorry, not enough tickets.");
            } else{
                setFirstClass(getFirstClass() - count);
                System.out.println("Thanks for buying!");
            }
        }
        public void sellEcoClassTickets(int count) throws Exception{
            if(count > getEcoClass() || count <= 0){
                throw new Exception("Sorry, not enough tickets.");
            } else{
                setEcoClass(getEcoClass() - count);
            }
        }
    }
    public Flight(Airport placeOut, Airport placeIn, Instant timeOut, Instant timeIn, Seats seats){
        this.id = placeOut.getCiti().toUpperCase().substring(0, 3) + placeIn.getCiti().toUpperCase().substring(0, 3) + timeIn;
        this.timeOut = timeOut;
        this.timeIn = timeIn;
        this.placeIn = placeIn;
        this.placeOut = placeOut;
        this.count = seats;
    }


    public Instant getTimeIn(){return timeIn;}
    public Instant getTimeOut(){return timeOut;}
    public String getId() {return id;}
    public Duration getTimeInAir() {return Duration.between(getTimeIn(), getTimeOut());}
    public Seats getSeats() {return count;}
    public Airport getPlaceOut(){return placeOut;}
    public Airport getPlaceIn(){return placeIn;}


    public void setTimeOut(Instant timOut){timeOut = timOut;}
    public void setTimeIn(Instant timIn){
        timeIn = timIn;
    }
}
