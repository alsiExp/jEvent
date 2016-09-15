package ru.jevent.model;

import ru.jevent.model.Enums.RateType;

import java.time.LocalDate;

public class Rate extends NamedEntity {
    // tariffs for events:
    // online / personal
    // lite / standart / business

    private RateType rateType;
    private LocalDate start;
    private LocalDate end;
    private double cost;


    public Rate() {
    }

    public Rate(String name, RateType rateType, LocalDate start, LocalDate end, double cost) {
        super(name);
        this.rateType = rateType;
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public Rate(long id, String name, RateType rateType, LocalDate start, LocalDate end, double cost) {
        super(id, name);
        this.rateType = rateType;
        this.start = start;
        this.end = end;
        this.cost = cost;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rate)) return false;
        if (!super.equals(o)) return false;

        Rate rate = (Rate) o;

        if (Double.compare(rate.cost, cost) != 0) return false;
        if (rateType != rate.rateType) return false;
        if (start != null ? !start.equals(rate.start) : rate.start != null) return false;
        return end != null ? end.equals(rate.end) : rate.end == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (rateType != null ? rateType.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Rate{" +
                super.toString() +
                ", rateType=" + rateType +
                ", start=" + start +
                ", end=" + end +
                ", cost=" + cost +
                "} ";
    }
}
