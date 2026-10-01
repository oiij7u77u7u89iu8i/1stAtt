package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static java.lang.Math.abs;

public class Route{
    private List<Segment> list;


    /**
     * Конструирует объект маршрута и выполняет строгую валидацию цепочки перелетов.
     * Проверяет связность городов пересадки и минимальное время на транзит.
     *
     * @param list        список сегментов перелета, составляющих маршрут
     * @throws MyException если список пуст, города не совпадают или нарушено время стыковки
     */

    public Route(List<Segment> list) throws MyException{
        if(list.isEmpty()){
            throw new MyException("U can't buy null ticket");
        }
        for(int i = 0; i < list.size() - 1; i++){
            Flight current = list.get(i).flight();
            Flight next = list.get(i + 1).flight();


            if(!current.getPlaceIn().getCiti().equals(next.getPlaceOut().getCiti())){
                throw new MyException("You can't get flight in another city!");
            }
            if(!current.getTimeIn().isBefore(next.getTimeOut()) || abs(Duration.between(current.getTimeIn(), next.getTimeOut()).toMinutes()) <= 45){
                throw new MyException("You can't get flight after flight is gone!");
            }
        }
        this.list = new java.util.ArrayList<>(list);
    }
    public List<Segment> getList() { return List.copyOf(list); }

    public Duration getAllTime(){
        return Duration.between(list.getFirst().flight().getTimeOut(), list.getLast().flight().getTimeIn());
    }

    public Duration getLayoverTime() {
        Duration dur = Duration.ZERO;
        for (int i = 0; i < list.size() - 1; i++) {
            dur = dur.plus(Duration.between(
                    list.get(i).flight().getTimeIn(),
                    list.get(i + 1).flight().getTimeOut()
            ));
        }
        return dur;
    }

    public Duration getFlightTime() {
        Duration totalFlightTime = Duration.ZERO;
        for (Segment segment : list) {
            totalFlightTime = totalFlightTime.plus(segment.flight().getTimeInAir());
        }
        return totalFlightTime;
    }



    /**
     * Формирует и выводит в консоль подробное текстовое расписание всего маршрута,
     * включая города отправления/прибытия, локальное время, индикаторы смещения дней (+1 day)
     * и время в воздухе для каждого отдельного сегмента.
     */

    public void FlyInf() {
        StringBuilder sb = new StringBuilder("Your trip:\n");
        for (int i = 0; i < list.size(); i++) {
            Flight f = list.get(i).flight();
            sb.append(f.getPlaceOut().getCiti())
                    .append(" ")
                    .append(f.getTimeOutLocal())
                    .append(" -> ")
                    .append(f.getPlaceIn().getCiti())
                    .append(" Time in: ")
                    .append(f.getTimeInLocal());
            if (!f.getTimeInLocal().toLocalDate().equals(f.getTimeOutLocal().toLocalDate())) {
                long days = java.time.temporal.ChronoUnit.DAYS.between(
                        f.getTimeOutLocal().toLocalDate(),
                        f.getTimeInLocal().toLocalDate()
                );
                sb.append(" (+").append(days).append(" day)");
            }

            sb.append(" Time in air: ")
                    .append(f.getTimeInAir().toHours())
                    .append("h ")
                    .append(f.getTimeInAir().toMinutesPart())
                    .append("m\n");
        }
        System.out.println(sb.toString());
    }


}


