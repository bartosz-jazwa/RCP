package database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Activity")
public class Activity implements Serializable {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "name")
    private String name;

    public Activity() {
    }

    public Activity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
