package ru.jevent.model;


import ru.jevent.LoggerWrapper;

public class BaseEntity {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(BaseEntity.class);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        if (id == null || that.id == null) {
            throw LOG.getIllegalStateException("Equals '" + this + "' and '" + that + "' with null id");
        }
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : Long.hashCode(id);
    }

}
