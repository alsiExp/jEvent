package ru.jevent.model;


import javax.persistence.*;

@Entity
@Table(name = "partners")
@NamedQueries({
        @NamedQuery(name = "Partner.delete", query = "DELETE from Partner p where p.id = :id"),
        @NamedQuery(name = "Partner.getAllSorted", query = "SELECT p FROM Partner p ORDER BY p.id")
})
public class Partner extends NamedEntity implements Attachable {

    public static final String DELETE = "Partner.delete";
    public static final String ALL_SORTED = "Partner.getAllSorted";

    //    connection info
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    //    logo
    @Column(name = "logo_url")
    private String logoURL;


    public Partner() {
    }

    public Partner(String name, String email, String phone, String description, String logoURL) {
        super(name);
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.logoURL = logoURL;
    }

    public Partner(long id, String name, String email, String phone, String description, String logoURL) {
        super(id, name);
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.logoURL = logoURL;
    }


    @Override
    public String getAttachName() {
        return this.getName();
    }

    @Override
    public String getAttachDescription() {
        return this.getDescription();
    }

    @Override
    public String getAttachImageURL() {
        return this.getLogoURL();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partner)) return false;
        if (!super.equals(o)) return false;

        Partner partner = (Partner) o;

        if (email != null ? !email.equals(partner.email) : partner.email != null) return false;
        if (phone != null ? !phone.equals(partner.phone) : partner.phone != null) return false;
        if (description != null ? !description.equals(partner.description) : partner.description != null) return false;
        return logoURL != null ? logoURL.equals(partner.logoURL) : partner.logoURL == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (logoURL != null ? logoURL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Partner{" +
                super.toString() +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", logoURL='" + logoURL + '\'' +
                "} ";
    }
}
