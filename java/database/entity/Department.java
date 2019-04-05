package database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Department implements Serializable {
    @Id
    @Column(name = "Name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, Employee head) {
        this.name = name;
        //this.head = head;
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
    }

    public Set<Employee> getEmployees() {
        return employees;
    }
}
