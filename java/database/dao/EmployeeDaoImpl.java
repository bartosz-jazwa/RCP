package database.dao;

import database.entity.Credentials;
import database.entity.Employee;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Optional;

public class EmployeeDaoImpl extends DaoImpl {
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

    public Optional<Employee> get(Integer id) {
        Session session = super.getSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class,id);
        session.close();
        return Optional.ofNullable(employee);
    }

    public Optional<Employee> get(Credentials c) {
        Session session = super.getSession();
        session.beginTransaction();

        Employee employee = (Employee) session.createCriteria(Employee.class).add(Restrictions.eq("credentials", c)).uniqueResult();
        Optional<Employee> optionalEmployee = Optional.ofNullable(employee);

        //session.disconnect();
        session.close();

        return optionalEmployee;
    }
}
