package database.dao;

import database.entity.Employee;
import database.entity.Entry;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntryDaoImpl extends DaoImpl {
    public EntryDaoImpl() {
        super(Entry.class);
    }

    public void delete(Entry entry) {
        super.delete(entry);
    }

    public List<Entry> getByDate(Employee employee, LocalDate date) {
        Session session = super.getSession();
        session.beginTransaction();
        List<Entry> entries = session.createCriteria(Entry.class).add(Restrictions.eq("employee", employee)).add(Restrictions.eq("dates",date)).list();
        return entries;
    }
    public Set<Entry> getByMonth(Employee employee, LocalDate date){
        Session session = super.getSession();
        session.beginTransaction();

        LocalDate firstDay = LocalDate.of(date.getYear(),date.getMonth(),1);
        LocalDate lastDay = LocalDate.of(date.getYear(),date.getMonth(),date.lengthOfMonth());
        List<Entry> entries = session.createCriteria(Entry.class)
                .add(Restrictions.eq("employee", employee))
                .add(Restrictions.between("dates",firstDay,lastDay)).list();

        session.close();
        return entries.stream()
                .distinct()
                .collect(Collectors.toSet());
    }

}
