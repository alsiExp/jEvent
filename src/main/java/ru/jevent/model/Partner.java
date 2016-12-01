package ru.jevent.model;


import ru.jevent.model.converter.PartnerStatusConverter;
import ru.jevent.model.enums.PartnerStatus;
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "events_partners",
            joinColumns = @JoinColumn(name = "partner_id", referencedColumnName = "status_id"))
    @Convert(converter = PartnerStatusConverter.class)
    private PartnerStatus status;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_id")
    private Set<Speech> speechSet;

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

    public PartnerStatus getStatus() {
        return status;
    }

    public void setStatus(PartnerStatus status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partner)) return false;
        if (!super.equals(o)) return false;

        Partner partner = (Partner) o;

        if (status != partner.status) return false;
        if (contactEmail != null ? !contactEmail.equals(partner.contactEmail) : partner.contactEmail != null)
            return false;
        if (contactPhone != null ? !contactPhone.equals(partner.contactPhone) : partner.contactPhone != null)
            return false;
        if (contactName != null ? !contactName.equals(partner.contactName) : partner.contactName != null) return false;
        if (description != null ? !description.equals(partner.description) : partner.description != null) return false;
        if (logoURL != null ? !logoURL.equals(partner.logoURL) : partner.logoURL != null) return false;
        return speechSet != null ? speechSet.equals(partner.speechSet) : partner.speechSet == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (contactEmail != null ? contactEmail.hashCode() : 0);
        result = 31 * result + (contactPhone != null ? contactPhone.hashCode() : 0);
        result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (logoURL != null ? logoURL.hashCode() : 0);
        result = 31 * result + (speechSet != null ? speechSet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder speechSB = new StringBuilder();
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
        return "Partner{" +
                super.toString() +
                ", contact name='" + contactName + '\'' +
                ", contact phone='" + contactPhone + '\'' +
                ", speeches='" + speechSB + '\'' +
                "} ";
    }
}
