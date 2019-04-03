package delegation.entity;

import database.entity.Employee;
import database.entity.Project;

import java.time.Year;
import java.util.List;
import java.util.Locale;

public class Delegation {
    private long number;
    private Employee employee;
    private Project project;
    private Locale country;
    private TravelTo travelTo;
    private TravelBack travelBack;
    private float advance;
    private List<Bill> bills;

    public long calculateDuration(){

        long days;
        if (travelBack.getCrossBorderTime().getYear()==travelTo.getCrossBorderTime().getYear()) {
            days = travelBack.getCrossBorderTime().getDayOfYear() - travelTo.getCrossBorderTime().getDayOfYear()+1;
        }else{
            if(Year.from(travelTo.getCrossBorderTime()).isLeap() && travelBack.getCrossBorderTime().getDayOfYear()<60){
                days = travelBack.getCrossBorderTime().getDayOfYear() - travelTo.getCrossBorderTime().getDayOfYear()+366+1;
            }else {
                days = travelBack.getCrossBorderTime().getDayOfYear() - travelTo.getCrossBorderTime().getDayOfYear()+365+1;
            }
        }

        return days;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Locale getCountry() {
        return country;
    }

    public void setCountry(Locale country) {
        this.country = country;
    }

    public TravelTo getTravelTo() {
        return travelTo;
    }

    public void setTravelTo(TravelTo travelTo) {
        this.travelTo = travelTo;
    }

    public TravelBack getTravelBack() {
        return travelBack;
    }

    public void setTravelBack(TravelBack travelBack) {
        this.travelBack = travelBack;
    }

    public float getAdvance() {
        return advance;
    }

    public void setAdvance(float advance) {
        this.advance = advance;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
}
