package org.example;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String name;
        Route usersRoute;
        try {
            Airport moscow = new Airport(ZoneId.of("Europe/Moscow"));
            Airport dubai = new Airport(ZoneId.of("Asia/Dubai"));
            Airport tokyo = new Airport(ZoneId.of("Asia/Tokyo"));
            Airport newYork = new Airport(ZoneId.of("America/New_York"));
            Airport london = new Airport(ZoneId.of("Europe/London"));
            Airport paris = new Airport(ZoneId.of("Europe/Paris"));

            List<Airport> airports = List.of(moscow, dubai, tokyo, newYork, london, paris);
            List<Flight> flights = generateFlights(moscow, dubai, tokyo, newYork, london, paris);

            System.out.println("Enter your name: ");
            Scanner scanner = new Scanner(System.in);
            name = scanner.nextLine();

            System.out.println("\nДоступные аэропорты:");
            for (int i = 0; i < airports.size(); i++) {
                System.out.println((i + 1) + ". " + airports.get(i).getZoneId());
            }

            Airport origin = null;

            while (true){
                try{
                    System.out.print("\nВыберите номер пункта отправления: ");
                    int originIdx = scanner.nextInt() - 1;
                    if(originIdx < airports.size() && originIdx >= 0) {
                        origin = airports.get(originIdx);
                        break;
                    }
                }catch (Exception e){
                    System.out.println("Введите число");
                    scanner.nextLine();
                }
            }

            Airport destination = null;
            while (true) {
                try{
                    System.out.print("Выберите номер пункта назначения: ");
                    int destIdx = scanner.nextInt() - 1;
                    Airport selectedDestination = airports.get(destIdx);
                    if (origin.equals(selectedDestination)) {
                        System.out.println("Пункт отправления и назначения совпадают!");
                    }else{
                        destination = selectedDestination;
                        break;
                    }
                }catch (Exception e){
                    System.out.println("Введите число < " + airports.size());
                    scanner.nextLine();
                }finally {
                    System.out.println("Пункт выбран");
                }
            }

            usersRoute = searchRoute(origin, destination, flights);
            System.out.println("\nМаршрут успешно выбран!");

            Booking booking = new Booking(usersRoute, name, new StandardDiscount());

            System.out.println("\nМаршрут успешно выбран и сохранен!");
            System.out.println("Статус бронирования: " + booking.getStatus());
            System.out.println("Итоговая стоимость: " + booking.getPrice() + " у.е.");
            System.out.println();

            usersRoute.FlyInf();

        } catch (MyException e) {
            System.err.println("Ошибка валидации маршрута или бронирования: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Flight> generateFlights(Airport mow, Airport dxb, Airport nrt,
                                                Airport jfk, Airport lhr, Airport cdg) {
        List<Flight> list = new ArrayList<>();

        list.add(new Flight(mow, dxb, Instant.parse("2026-09-01T08:00:00Z"), Instant.parse("2026-09-01T13:30:00Z"), 220));
        list.add(new Flight(mow, dxb, Instant.parse("2026-09-01T14:00:00Z"), Instant.parse("2026-09-01T19:30:00Z"), 240));
        list.add(new Flight(mow, lhr, Instant.parse("2026-09-01T07:00:00Z"), Instant.parse("2026-09-01T11:00:00Z"), 260));
        list.add(new Flight(mow, cdg, Instant.parse("2026-09-01T10:00:00Z"), Instant.parse("2026-09-01T14:00:00Z"), 230));
        list.add(new Flight(dxb, nrt, Instant.parse("2026-09-01T16:00:00Z"), Instant.parse("2026-09-02T01:30:00Z"), 350));
        list.add(new Flight(dxb, nrt, Instant.parse("2026-09-01T22:30:00Z"), Instant.parse("2026-09-02T08:00:00Z"), 320));
        list.add(new Flight(dxb, lhr, Instant.parse("2026-09-01T16:30:00Z"), Instant.parse("2026-09-01T23:30:00Z"), 290));
        list.add(new Flight(dxb, jfk, Instant.parse("2026-09-02T02:00:00Z"), Instant.parse("2026-09-02T16:00:00Z"), 550));

        list.add(new Flight(lhr, jfk, Instant.parse("2026-09-01T14:00:00Z"), Instant.parse("2026-09-01T22:00:00Z"), 380));
        list.add(new Flight(lhr, jfk, Instant.parse("2026-09-02T10:00:00Z"), Instant.parse("2026-09-02T18:00:00Z"), 410));
        list.add(new Flight(lhr, cdg, Instant.parse("2026-09-01T13:00:00Z"), Instant.parse("2026-09-01T14:15:00Z"), 90));
        list.add(new Flight(lhr, cdg, Instant.parse("2026-09-01T18:00:00Z"), Instant.parse("2026-09-01T19:15:00Z"), 95));
        list.add(new Flight(lhr, nrt, Instant.parse("2026-09-01T15:00:00Z"), Instant.parse("2026-09-02T03:00:00Z"), 500));
        list.add(new Flight(cdg, jfk, Instant.parse("2026-09-01T16:30:00Z"), Instant.parse("2026-09-02T01:00:00Z"), 420));
        list.add(new Flight(cdg, dxb, Instant.parse("2026-09-02T08:00:00Z"), Instant.parse("2026-09-02T15:00:00Z"), 260));

        list.add(new Flight(nrt, jfk, Instant.parse("2026-09-02T11:00:00Z"), Instant.parse("2026-09-02T23:00:00Z"), 490));
        list.add(new Flight(nrt, dxb, Instant.parse("2026-09-02T12:00:00Z"), Instant.parse("2026-09-02T21:30:00Z"), 340));

        list.add(new Flight(jfk, lhr, Instant.parse("2026-09-02T20:00:00Z"), Instant.parse("2026-09-03T03:00:00Z"), 360));
        list.add(new Flight(jfk, cdg, Instant.parse("2026-09-02T22:00:00Z"), Instant.parse("2026-09-03T05:30:00Z"), 390));

        return list;
    }




    /**
     * Выполняет поиск доступных авиамаршрутов между заданными аэропортами.
     * Находит прямые рейсы и транзитные варианты с одной пересадкой,
     * валидируя время стыковки (от 45 минут до 24 часов).
     *
     * @param origin      аэропорт отправления выбранный пользователем
     * @param destination аэропорт назначения выбранный пользователем
     * @param list        общий список всех существующих рейсов в системе
     * @return            выбранный пользователем и валидированный маршрут
     * @throws MyException если подходящих рейсов или стыковок не найдено
     */

    public static Route searchRoute(Airport origin, Airport destination, List<Flight> list) throws MyException {
        List<List<Flight>> options = new LinkedList<>();

        for (Flight fly : list) {
            if (fly.getPlaceOut().equals(origin) && fly.getPlaceIn().equals(destination)) {
                options.add(Collections.singletonList(fly));
            }
        }

        for (Flight f1 : list) {
            if (f1.getPlaceOut().equals(origin)) {
                for (Flight f2 : list) {
                    if (f1.getPlaceIn().equals(f2.getPlaceOut()) && f2.getPlaceIn().equals(destination)) {
                        long layoverMinutes = Duration.between(f1.getTimeIn(), f2.getTimeOut()).toMinutes();
                        if (layoverMinutes >= 45 && layoverMinutes <= 24 * 60) {
                            options.add(List.of(f1, f2));
                        }
                    }
                }
            }
        }

        if (options.isEmpty()) {
            throw new MyException("Доступных маршрутов не найдено.");
        }

        System.out.println("\nДоступные маршруты:");
        for (int i = 0; i < options.size(); i++) {
            List<Flight> flights = options.get(i);
            System.out.print((i + 1) + ". " + flights.getFirst().getPlaceOut().getCiti() + " " + flights.getFirst().getTimeOut());
            for (Flight f : flights) {
                System.out.print(" -> " + f.getPlaceIn().getCiti() + " " + flights.getFirst().getTimeOut());
            }
            System.out.println();
        }

        int choice = -1;
        int maxChoice = options.size();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("\nВыберите маршрут (1 - " + maxChoice + "): ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice >= 1 && choice <= maxChoice) {
                    break;
                } else {
                    System.out.println("Ошибка: введите число от 1 до " + maxChoice);
                }
            } else {
                System.out.println("Ошибка: вы ввели не число.");
                sc.next();
            }
        }

        List<Flight> chosenFlights = options.get(choice - 1);
        SeatsClass selectedClass = selectClass();

        List<Segment> finalSegments = new ArrayList<>();
        for (Flight f : chosenFlights) {
            finalSegments.add(new Segment(f, selectedClass));
        }

        return new Route(finalSegments);
    }

    public static SeatsClass selectClass() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nВыберите класс обслуживания:");
        System.out.println("1. FIRST\n2. BUSINESS\n3. ECONOMY");

        while (true) {
            System.out.print("Введите номер (1-3): ");
            if (sc.hasNextInt()) {
                int cur = sc.nextInt();
                if (cur >= 1 && cur <= 3) {
                    return SeatsClass.getClasses().get(cur - 1);
                } else {
                    System.out.println("Ошибка: Выберите от 1 до 3");
                }
            } else {
                System.out.println("Ошибка: введите корректное число.");
                sc.next();
            }
        }
    }
}
