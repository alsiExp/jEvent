package ru.jevent.model;


public class BaseEntity {

    protected Long id;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isNew() {
        return (this.id == null);
    }
}
