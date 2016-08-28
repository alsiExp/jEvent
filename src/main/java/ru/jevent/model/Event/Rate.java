package ru.jevent.model.Event;

import ru.jevent.model.NamedEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Rate extends NamedEntity {
    // tariffs for events:
    // online / personal
    // lite / standart / business


    public Rate() {
    }

    private RateType rateType;
    private LocalDate start;
    private LocalDate end;
    private double cost;

    public enum RateType {
        ONLINE_LITE,
        ONLINE_STANDART,
        ONLINE_BUSINESS,
        PERSOONAL_LITE,
        PERSOONAL_STANDART,
        PERSOONAL_BUSINESS
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
