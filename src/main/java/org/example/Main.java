package org.example;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Создаем аэропорты в разных часовых поясах
            Airport moscow = new Airport(ZoneId.of("Europe/Moscow"));
            Airport dubai = new Airport(ZoneId.of("Asia/Dubai"));
            Airport tokyo = new Airport(ZoneId.of("Asia/Tokyo"));

            // 2. Создаем рейсы через системные часы (ProjectClock.START_TIME = 2026-09-05T12:00:00Z)
            // Рейс 1: Москва -> Дубай (вылет в 14:00 UTC, прилет в 19:30 UTC — длительность 5ч 30м)
            Instant flight1Out = Instant.parse("2026-09-05T14:00:00Z");
            Instant flight1In = Instant.parse("2026-09-05T19:30:00Z");
            Flight f1 = new Flight(moscow, dubai, flight1Out, flight1In, 180);

            // Рейс 2: Дубай -> Токио (стыковка 2 часа: вылет в 21:30 UTC, прилет на следующие сутки в 07:00 UTC)
            Instant flight2Out = Instant.parse("2026-09-05T21:30:00Z");
            Instant flight2In = Instant.parse("2026-09-06T07:00:00Z");
            Flight f2 = new Flight(dubai, tokyo, flight2Out, flight2In, 250);

            // 3. Формируем сегменты с классами обслуживания
            Segment s1 = new Segment(f1, SeatsClass.ECONOMY);
            Segment s2 = new Segment(f2, SeatsClass.BUSINESS);

            // 4. Собираем маршрут и выводим информацию
            Route route = new Route(List.of(s1, s2));
            route.FlyInf();

            System.out.println("Суммарное время в воздухе: " + route.getFlightTime().toHours() + "ч "
                    + route.getFlightTime().toMinutesPart() + "м");
            System.out.println("Суммарное время пересадок: " + route.getLayoverTime().toHours() + "ч "
                    + route.getLayoverTime().toMinutesPart() + "м");
            System.out.println("Всего времени в пути: " + route.getAllTime().toHours() + "ч "
                    + route.getAllTime().toMinutesPart() + "м\n");

            // 5. Демонстрация полиморфизма (расчет цен по разным стратегиям)
            DiscountStrategy standardTariff = new StandardDiscount();
            DiscountStrategy studentTariff = new StudentDiscount();

            Booking booking1 = new Booking(route, "Иван Иванов", standardTariff);
            Booking booking2 = new Booking(route, "Петр Смирнов (Студент)", studentTariff);

            System.out.println("Пассажир: " + booking1.getName() + " | Статус: " + booking1.getStatus()
                    + " | К оплате: $" + booking1.getPrice());
            System.out.println("Пассажир: " + booking2.getName() + " | Статус: " + booking2.getStatus()
                    + " | К оплате: $" + booking2.getPrice());

            // 6. Проверка отмены бронирования
            booking1.cancel();
            System.out.println("После отмены статус: " + booking1.getStatus());

        } catch (MyException e) {
            System.err.println("Ошибка валидации маршрута или бронирования: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}