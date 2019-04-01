package database.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Dao<T,C extends Serializable>  {
    void save(T t);
    void delete(T t);
    Optional<T> get(C id);
    List<T> getAll();
}
