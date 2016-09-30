package ru.jevent.model;


import ru.jevent.LoggerWrapper;

import javax.persistence.*;
import java.util.*;

@MappedSuperclass
@Access(AccessType.FIELD)
public class BaseEntity {

    protected static final LoggerWrapper LOG = LoggerWrapper.get(BaseEntity.class);

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
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

    @Override
    public String toString() {
        return "id=" + id;
    }

    protected final class EntityComparator implements Comparator<BaseEntity> {
        @Override
        public int compare(BaseEntity o1, BaseEntity o2) {
            Long l = o2.getId() - o1.getId();
            return l.hashCode();
        }
    }

    protected <T> boolean isEquals(Set<T> s1, Set<T> s2) {
        if(s1 == s2) return true;
        if(s1 == null && s2 != null || s1 != null && s2 == null) {
            return false;
        }
        if(s1.size() != s2.size()) {
            return false;
        }

        HashSet<T> notEquals = new HashSet<>();
        notEquals.addAll(s1);
        notEquals.addAll(s2);
        if(notEquals.size() != s1.size()) {
            //for debug
            notEquals.removeAll(s1);
            return false;
        }
        return true;
    }

    protected <T extends BaseEntity> boolean isEquals(List<T> l1, List<T> l2) {
        if(l1 == l2) return true;
        if(l1 == null && l2 != null || l1 != null && l2 == null) {
            return false;
        }
        if(l1.size() != l2.size()) {
            return false;
        }
        List <T> list1 = new ArrayList<T>(l1);
        List <T> list2 = new ArrayList<T>(l2);
        Collections.sort(list1, new EntityComparator());
        Collections.sort(list2, new EntityComparator());
        ListIterator<T> it1 = list1.listIterator();
        ListIterator<T> it2 = list2.listIterator();
        while(it1.hasNext() && it2.hasNext()) {
            T entity1 = it1.next();
            T entity2 = it2.next();
            if(entity1==null ? entity2==null : !entity1.equals(entity2)) {
                return false;
            }
        }
        return true;
    }
}
