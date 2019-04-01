package database.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Project")
public class Project {
    @Id
    @Column(name = "Name", unique = true, nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private Set<Activity> activities;

    public Project(String name, Set<Activity> activities) {
        this(name);
        this.activities = activities;
    }

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }
}
