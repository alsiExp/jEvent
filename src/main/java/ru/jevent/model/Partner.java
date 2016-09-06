package ru.jevent.model;

public class Partner extends NamedEntity implements Attachable {

    private String description;
    private byte[] image;
    private Person contact;


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
    public byte[] getAttachImage() {
        return this.getImage();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Person getContact() {
        return contact;
    }

    public void setContact(Person contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                "name=" + name +
                '}';
    }
}
