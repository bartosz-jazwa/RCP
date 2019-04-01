package database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Entry implements Serializable{
    @ManyToOne
    @JoinColumn(name = "employee")
    @Id
    private Employee employee;
    @Id
    private LocalDate dates;
    @Id
    private LocalTime startHour;
    @Id
    private LocalTime finishHour;
    @Column(name = "duration")
    private double duration;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Project project;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Activity activity;

    public Entry(Employee employee, LocalDate date, LocalTime startHour, LocalTime finishHour, Project project, Activity activity) {
        this.employee = employee;
        this.dates = date;
        this.startHour = startHour;
        this.finishHour = finishHour;
        this.activity = activity;
        this.project = project;
        this.duration = calcDuration(startHour,finishHour);
    }

    public Entry() {
    }

    private Double calcDuration(LocalTime start, LocalTime stop){
        int startHours = start.getHour();
        int startMinutes = start.getMinute();
        int stopHours = stop.getHour();
        int stopMinutes = stop.getMinute();
        int resultMinutes = (stopHours - startHours)*60 + (stopMinutes - startMinutes);
        Double result = new Double(resultMinutes);
        result = result/60.0;
        return BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double getDuration() {
        return duration;
    }

    public String getProjectName() {
        return project.getName();
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public LocalTime getFinishHour() {
        return finishHour;
    }

    public String getActivityName() {
        return activity.getName();
    }

    public LocalDate getDate() {
        return dates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry entry = (Entry) o;
        return Objects.equals(employee, entry.employee) &&
                Objects.equals(dates, entry.dates) &&
                Objects.equals(startHour, entry.startHour) &&
                Objects.equals(finishHour, entry.finishHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, dates, startHour, finishHour);
    }
}