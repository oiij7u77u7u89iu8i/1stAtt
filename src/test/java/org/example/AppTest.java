package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private Airport moscow;
    private Airport dubai;
    private Airport tokyo;
    private Airport newYork;

    @BeforeEach
    void setUp() {
        moscow = new Airport(ZoneId.of("Europe/Moscow"));
        dubai = new Airport(ZoneId.of("Asia/Dubai"));
        tokyo = new Airport(ZoneId.of("Asia/Tokyo"));
        newYork = new Airport(ZoneId.of("America/New_York"));
    }

    @Test
    void testFlightDurationWestboundLocalTimeEarlier() {
        Instant out = Instant.parse("2026-09-10T15:00:00Z");
        Instant in = Instant.parse("2026-09-10T23:00:00Z");

        Flight flight = new Flight(moscow, newYork, out, in, 200);

        assertEquals(Duration.ofHours(8), flight.getTimeInAir());
        assertEquals(18, flight.getTimeOutLocal().getHour());
        assertEquals(19, flight.getTimeInLocal().getHour());
    }

    @Test
    void testValidRouteCreation() throws Exception {
        Instant f1Out = Instant.parse("2026-09-10T10:00:00Z");
        Instant f1In = Instant.parse("2026-09-10T15:00:00Z");
        Flight f1 = new Flight(moscow, dubai, f1Out, f1In, 150);

        Instant f2Out = Instant.parse("2026-09-10T17:00:00Z");
        Instant f2In = Instant.parse("2026-09-11T01:00:00Z");
        Flight f2 = new Flight(dubai, tokyo, f2Out, f2In, 150);

        Route route = new Route(List.of(
                new Segment(f1, SeatsClass.ECONOMY),
                new Segment(f2, SeatsClass.BUSINESS)
        ));

        assertEquals(2, route.getList().size());
        assertEquals("Dubai", route.getList().get(0).flight().getPlaceIn().getCiti());
    }

    @Test
    void testLayoverAndFlightTimeCalculation() throws Exception {
        Instant f1Out = Instant.parse("2026-09-10T10:00:00Z");
        Instant f1In = Instant.parse("2026-09-10T14:00:00Z");
        Flight f1 = new Flight(moscow, dubai, f1Out, f1In, 100);

        Instant f2Out = Instant.parse("2026-09-10T15:30:00Z");
        Instant f2In = Instant.parse("2026-09-10T20:30:00Z");
        Flight f2 = new Flight(dubai, tokyo, f2Out, f2In, 100);

        Route route = new Route(List.of(
                new Segment(f1, SeatsClass.ECONOMY),
                new Segment(f2, SeatsClass.ECONOMY)
        ));

        assertEquals(Duration.ofMinutes(90), route.getLayoverTime());
        assertEquals(Duration.ofHours(9), route.getFlightTime());
        assertEquals(Duration.ofMinutes(630), route.getAllTime());
    }

    @Test
    void testRouteThrowsWhenLayoverLessThan45Minutes() {
        Instant f1Out = Instant.parse("2026-09-10T10:00:00Z");
        Instant f1In = Instant.parse("2026-09-10T14:00:00Z");
        Flight f1 = new Flight(moscow, dubai, f1Out, f1In, 100);

        Instant f2Out = Instant.parse("2026-09-10T14:30:00Z");
        Instant f2In = Instant.parse("2026-09-10T19:00:00Z");
        Flight f2 = new Flight(dubai, tokyo, f2Out, f2In, 100);

        assertThrows(MyException.class, () -> new Route(List.of(
                new Segment(f1, SeatsClass.ECONOMY),
                new Segment(f2, SeatsClass.ECONOMY)
        )));
    }

    @Test
    void testRouteThrowsWhenCitiesDoNotMatch() {
        Instant f1Out = Instant.parse("2026-09-10T10:00:00Z");
        Instant f1In = Instant.parse("2026-09-10T14:00:00Z");
        Flight f1 = new Flight(moscow, dubai, f1Out, f1In, 100);

        Instant f2Out = Instant.parse("2026-09-10T16:00:00Z");
        Instant f2In = Instant.parse("2026-09-10T22:00:00Z");
        Flight f2 = new Flight(newYork, tokyo, f2Out, f2In, 100);

        assertThrows(MyException.class, () -> new Route(List.of(
                new Segment(f1, SeatsClass.ECONOMY),
                new Segment(f2, SeatsClass.ECONOMY)
        )));
    }

    @Test
    void testEmptyRouteThrowsException() {
        assertThrows(MyException.class, () -> new Route(new ArrayList<>()));
    }

    @Test
    void testRouteListImmutability() throws Exception {
        Instant out = Instant.parse("2026-09-10T10:00:00Z");
        Instant in = Instant.parse("2026-09-10T14:00:00Z");
        Flight f1 = new Flight(moscow, dubai, out, in, 100);

        List<Segment> list = new ArrayList<>();
        list.add(new Segment(f1, SeatsClass.ECONOMY));

        Route route = new Route(list);
        list.clear();

        assertEquals(1, route.getList().size());
        assertThrows(UnsupportedOperationException.class, () -> route.getList().add(new Segment(f1, SeatsClass.FIRST)));
    }

    @Test
    void testSeatsClassMultipliers() {
        assertEquals(2.0, SeatsClass.FIRST.getPriceMult());
        assertEquals(1.5, SeatsClass.BUSINESS.getPriceMult());
        assertEquals(1.0, SeatsClass.ECONOMY.getPriceMult());
    }

    @Test
    void testStandardPricingCalculation() throws Exception {
        Instant out = Instant.parse("2026-09-10T10:00:00Z");
        Instant in = Instant.parse("2026-09-10T12:00:00Z");
        Flight f = new Flight(moscow, dubai, out, in, 100);

        Route route = new Route(List.of(new Segment(f, SeatsClass.ECONOMY)));
        Booking booking = new Booking(route, "Иван", new StandardDiscount());

        assertEquals(20.0, booking.getPrice(), 0.001);
    }

    @Test
    void testStudentPricingCalculation() throws Exception {
        Instant out = Instant.parse("2026-09-10T10:00:00Z");
        Instant in = Instant.parse("2026-09-10T12:00:00Z");
        Flight f = new Flight(moscow, dubai, out, in, 100);

        Route route = new Route(List.of(new Segment(f, SeatsClass.BUSINESS)));
        Booking booking = new Booking(route, "Студент", new StudentDiscount());

        assertEquals(25.5, booking.getPrice(), 0.001);
    }

    @Test
    void testBookingSuccessfulCancellation() throws Exception {
        Instant out = Instant.parse("2026-10-01T10:00:00Z");
        Instant in = Instant.parse("2026-10-01T14:00:00Z");
        Flight f = new Flight(moscow, dubai, out, in, 100);

        Route route = new Route(List.of(new Segment(f, SeatsClass.ECONOMY)));
        Booking booking = new Booking(route, "Пассажир", new StandardDiscount());

        assertEquals(BookingStatus.BOOKED, booking.getStatus());
        booking.cancel();
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
    }

    @Test
    void testBookingCannotBeCancelledTwice() throws Exception {
        Instant out = Instant.parse("2026-10-01T10:00:00Z");
        Instant in = Instant.parse("2026-10-01T14:00:00Z");
        Flight f = new Flight(moscow, dubai, out, in, 100);

        Route route = new Route(List.of(new Segment(f, SeatsClass.ECONOMY)));
        Booking booking = new Booking(route, "Пассажир", new StandardDiscount());

        booking.cancel();
        assertThrows(MyException.class, booking::cancel);
    }
}
