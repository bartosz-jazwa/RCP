package delegation.entity;

import database.entity.Employee;
import database.entity.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Application {
    private long number;
    private LocalDate applicationDate;
    private Employee employee;
    private Project project;
    private Locale country;
    private String transport;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Float advanceAmount;
    private Currency advanceCurrency;
    private ApplicationStatus status;
    private List<PlanItem> plan;

    public List<PlanItem> initPlan(LocalDate start, LocalDate finish){
        List<PlanItem> list = new ArrayList<>();
        for (LocalDate i = start; i.isBefore(finish); i.plusDays(1)) {
            list.add(new PlanItem(i,""));
        }
        return list;
    }
}
