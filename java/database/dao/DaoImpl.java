package database.dao;

import database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class DaoImpl<T, C extends Serializable> implements Dao<T, C> {

    private SessionFactory factory = HibernateUtil.getSessionFactory();
    private Session session = factory.openSession();
    private Class<T> entityClass;

    public DaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void save(T t) {
        //factory.openSession().beginTransaction();
        session.beginTransaction();
        session.saveOrUpdate(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(T t) {
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<T> get(C id) {
        session.beginTransaction();
        T t = session.get(entityClass,id);
        session.close();
        return Optional.ofNullable(t);
    }

    @Override
    public List<T> getAll() {

        session.beginTransaction();
        List<T> ts = session.createCriteria(entityClass).list();
        session.close();
        return ts;
    }

    protected Session getSession() {
        return session;
    }
}
