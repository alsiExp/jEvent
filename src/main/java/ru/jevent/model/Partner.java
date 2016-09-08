package ru.jevent.model;

public class Partner extends NamedEntity implements Attachable {

    //    connection info
    private String email;
    private String phone;

    private String description;

    //    logo
    private String logoURL;


    public Partner() {
    }

    public Partner(long id, String name) {
        super(id, name);
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
        return null;
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
    public String toString() {
        return "Partner{" +
                "id=" + id +
                "name=" + name +
                '}';
    }
}
