package org.example;


import java.sql.Time;
import java.util.Locale;

public class Flight {
    private String id;
    private Time timeOut;
    private Time timeIn ;
    private Airport placeOut;
    private Airport placein;
    private int timeInAir;
    private Seats count;

    public static class Seats{
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

        public void setEcoClass(int ecoClass) {
            this.ecoClass = ecoClass;
        }

        public void setFirstClass(int firstClass) {
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
    public Flight(Airport placeOut, Airport placein, Time timeOut, Time timeIn){
        this.id = ;
        this.timeOut = timeOut;
        this.timeIn = timeIn;
        this.placein = placein;
        this.placeOut = placeOut;
    }

    public String getId() {return id;}
    public int getTimeInAir() {return timeInAir;}
    public int[] getSeats() {return count;}
    public String getPlaceOut(){return placeOut;}


    public void setTimeOut(Time timOut){
        timeOut = timOut;
    }
    public void setTimeIn(Time timIn){
        timeIn = timIn;
    }
}
