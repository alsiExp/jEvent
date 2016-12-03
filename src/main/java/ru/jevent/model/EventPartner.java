package ru.jevent.model;

import ru.jevent.model.converter.PartnerStatusConverter;
import ru.jevent.model.enums.PartnerStatus;
import ru.jevent.model.superclasses.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "event_partners")
public class EventPartner extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(name = "status_id")
    @Convert(converter = PartnerStatusConverter.class)
    private PartnerStatus status;

    @Column(name = "payment")
    private Double payment;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public PartnerStatus getStatus() {
        return status;
    }

    public void setStatus(PartnerStatus status) {
        this.status = status;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventPartner)) return false;
        if (!super.equals(o)) return false;

        EventPartner that = (EventPartner) o;

        if (event.getId() != null ? !event.getId().equals(that.event.getId()) : that.event.getId() != null) return false;
        if (partner.getId() != null ? !partner.getId().equals(that.partner.getId()) : that.partner.getId() != null) return false;
        if (status != that.status) return false;
        return payment != null ? payment.equals(that.payment) : that.payment == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (event.getId() != null ? event.getId().hashCode() : 0);
        result = 31 * result + (partner.getId() != null ? partner.getId().hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventPartner{" +
                super.toString() +
                ", event=" + event.getName() + " " + event.getVersion() +
                ", partner=" + partner.getName() +
                ", status=" + status +
                ", payment=" + payment +
                '}';
    }
}
