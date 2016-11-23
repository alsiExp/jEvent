package ru.jevent.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @NotEmpty
    @Column(name = "name", nullable = false)
    protected String name;

    public NamedEntity() {
    }

    public NamedEntity(String name) {
        this.name = name;
    }

    public NamedEntity(long id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NamedEntity that = (NamedEntity) o;

        return name.equals(that.name);

    }

    // TODO: commit 2da2d96
    @Override
    public int hashCode() {
        int result = super.hashCode();
        //result = 31 * result + name.hashCode(); //here we have a problem with name
        return result;
    }

    @Override
    public String toString() {
        return  super.toString() +
                ", name='" + name + '\'';
    }
}
