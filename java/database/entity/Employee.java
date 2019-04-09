package database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Entity
public class Employee implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Embedded
    @Column(name = "Credentials")
    private Credentials credentials;
    @Column(name = "First_name")
    private String firstName;
    @Column(name = "Last_name")
    private String surname;
    @ManyToOne
    private Department department;
    @Column(name = "Position")
    private String position;
    @OneToMany(targetEntity = Entry.class, cascade = CascadeType.ALL, mappedBy = "employee",fetch = FetchType.LAZY)
    private Set<Entry> entries = new HashSet<>();

    public Employee() {
    }

    public Employee(String username, String password, String firstName, String surname, Department department, String position) {
        //this.id = id; //db powinno samo sobie nadawać
        this.credentials = new Credentials(username, password);
        this.firstName = firstName;
        this.surname = surname;
        this.department = department;
        this.department.addEmployee(this);
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void addEntry(LocalDate date, LocalTime start, LocalTime stop, Project project, Activity activity) {

        AtomicReference<LocalTime> calcStart = new AtomicReference<>(start);
        AtomicReference<LocalTime> calcStop = new AtomicReference<>(stop);

        if (stop.isBefore(start)){
            calcStop.set(start);
        }

        Duration duration= Duration.between(start,stop);

        Optional<Entry> entryMax = this.entries.stream()
                .filter(entry -> entry.getDate().equals(date))
                .max(Comparator.comparing(Entry::getFinishHour));
        entryMax.ifPresent(e -> {
            if (start.isBefore(e.getFinishHour())|| stop.isBefore(e.getFinishHour())){
                calcStart.set(e.getFinishHour());
                calcStop.set(e.getFinishHour().plus(duration));
            }

        });

        LocalTime newStart = calcStart.get();
        LocalTime newStop = calcStop.get();

        this.entries.add(new Entry(this, date, newStart, newStop, project, activity));
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    private Entry addEntry(LocalTime start, LocalTime stop, Project project, Activity activity) {
        return new Entry(this, LocalDate.now(), start, stop, project, activity);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getUsername() {
        return credentials.getUsername();
    }

    public String getHash() {
        return credentials.getHashedPassword();
    }

    public String getName() {
        return firstName +" "+surname;
    }

    public Set<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }
}