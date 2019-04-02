package delegation.entity;

import database.entity.Employee;
import database.entity.Project;

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

}
