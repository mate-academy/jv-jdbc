package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

@mate.jdbc.lib.Dao
public interface Dao<T> {
    T create(T t);
    Optional<T> get(Long id);
    List<T> getAll();
    T update(T t);
    boolean delete(Long id);
}
