package database.dao;

import database.entity.Credentials;
import database.entity.Employee;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.Optional;

public class EmployeeDaoImpl extends DaoImpl<Employee> {
    public EmployeeDaoImpl() {
        super(Employee.class);
    }

    public void save(Employee o) {
        super.save(o);
    }

    public void delete(Employee o) {
        super.delete(o);
    }

    public void delete(Integer id) {
        Session session = super.getSession();
        session.beginTransaction();
        session.createQuery("delete Employee where id=:id").setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    private Optional<Employee> get(Integer id) {
        Session session = super.getSession();
        session.beginTransaction();
        Optional<Employee> employee = super.get(id);
        session.close();
        return employee;
    }

    private Optional<Employee> get(Credentials c) {
        Session session = super.getSession();
        session.beginTransaction();

        Employee employee = (Employee) session.createCriteria(Employee.class).add(Restrictions.eq("credentials", c)).uniqueResult();
        Optional<Employee> optionalEmployee = Optional.ofNullable(employee);

        //session.disconnect();
        session.close();

        return optionalEmployee;
    }

    @Override
    public <C extends Serializable> Optional<Employee> get(C c) {
        return this.get((Credentials)c);
    }
}
