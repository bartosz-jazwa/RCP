package delegation.entity;

import database.entity.Employee;
import database.entity.Project;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Application {
    private long number;
    private Employee employee;
    private Project project;
    private Locale country;
    private String transport;
    private TravelTo travelTo;
    private TravelBack travelBack;
    private float advanceAmount;
    private Currency advanceCurrency;
    private ApplicationStatus status;
    private List<String> plan;
}
