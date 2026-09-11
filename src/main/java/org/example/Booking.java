package org.example;

public class Booking {
    private final Route route;
    private final String name;
    private BookingStatus status;
    private final double price;

    public Booking(Route route, String passName, DiscountStrategy discount) {
        this.name = passName;
        this.route = route;
        this.status = BookingStatus.BOOKED;
        this.price = discount.getPrice(route);
    }

    public String getName() { return name; }
    public Route getRoute() { return route; }
    public BookingStatus getStatus() { return status; }
    public double getPrice() { return price; }

    public void cancel() throws MyException {
        if (this.status == BookingStatus.CANCELLED) {
            throw new MyException("Бронирование уже отменено!");
        }

        // Проверка: нельзя отменить бронь, если первый рейс уже улетел относительно часов
        if (ProjectClock.now().isAfter(route.getList().getFirst().flight().getTimeOut())) {
            throw new MyException("Нельзя отменить бронь: рейс уже улетел!");
        }

        this.status = BookingStatus.CANCELLED;
    }
}