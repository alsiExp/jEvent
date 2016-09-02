package ru.jevent.model;

import ru.jevent.model.Enums.RateType;
import ru.jevent.model.NamedEntity;

import java.time.LocalDate;

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
