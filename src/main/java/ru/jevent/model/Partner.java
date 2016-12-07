package ru.jevent.model;


import ru.jevent.model.superclasses.NamedEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "partners")
@NamedQueries({
        @NamedQuery(name = "Partner.delete", query = "DELETE from Partner p where p.id = :id"),
        @NamedQuery(name = "Partner.getAllSorted", query = "SELECT p FROM Partner p ORDER BY p.id")
})
public class Partner extends NamedEntity {

    public static final String DELETE = "Partner.delete";
    public static final String ALL_SORTED = "Partner.getAllSorted";

    //    connection info
    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "description")
    private String description;

    //    logo
    @Column(name = "logo_url")
    private String logoURL;

    @OneToMany(mappedBy ="partner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Speech> speechSet;

    @OneToMany(mappedBy = "partner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventPartner> eventPartners;

    public Partner() {
    }


    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public Set<Speech> getSpeechSet() {
        if(speechSet == null) {
            speechSet = new HashSet<>();
        }
        return speechSet;
    }

    public void setSpeechSet(Set<Speech> speechSet) {
        this.speechSet = speechSet;
    }

    public Set<EventPartner> getEventPartners() {
        if(eventPartners == null) {
            eventPartners = new HashSet<>();
        }
        return eventPartners;
    }

    public void setEventPartners(Set<EventPartner> eventPartners) {
        this.eventPartners = eventPartners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partner)) return false;
        if (!super.equals(o)) return false;

        Partner partner = (Partner) o;

        if (contactEmail != null ? !contactEmail.equals(partner.contactEmail) : partner.contactEmail != null)
            return false;
        if (contactPhone != null ? !contactPhone.equals(partner.contactPhone) : partner.contactPhone != null)
            return false;
        if (contactName != null ? !contactName.equals(partner.contactName) : partner.contactName != null) return false;
        if (description != null ? !description.equals(partner.description) : partner.description != null) return false;
        if (logoURL != null ? !logoURL.equals(partner.logoURL) : partner.logoURL != null) return false;
        if (!isEquals(this.getEventPartners(), partner.getEventPartners())) {
            return false;
        }
        if (this.getSpeechSet().hashCode() != partner.getSpeechSet().hashCode()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (contactEmail != null ? contactEmail.hashCode() : 0);
        result = 31 * result + (contactPhone != null ? contactPhone.hashCode() : 0);
        result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (logoURL != null ? logoURL.hashCode() : 0);
        result = 31 * result + (speechSet != null ? speechSet.hashCode() : 0);
        result = 31 * result + (eventPartners != null ? eventPartners.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder speechSB = new StringBuilder();
        StringBuilder eventSB = new StringBuilder();
        if(!this.getSpeechSet().isEmpty()){
            String prefix = "";
            speechSB.append('[');
            for (Speech s : speechSet) {
                speechSB.append(prefix);
                prefix = ",";
                speechSB.append((s.getName()));
            }
            speechSB.append(']');
        }
        if(!this.getEventPartners().isEmpty()){
            String prefix = "";
            eventSB.append('[');
            for (EventPartner ep : eventPartners) {
                eventSB.append(prefix);
                prefix = ",";
                speechSB.append(ep.getEvent().getName()).append(" ").append(ep.getEvent().getVersion());
            }
            speechSB.append(']');
        }
        return "Partner{" +
                super.toString() +
                ", speeches='" + speechSB + '\'' +
                ", events='" + eventSB + '\'' +
                "} ";
    }
}
