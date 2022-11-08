package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    T create(T o);

    Optional<T> get(Object id);

    List<T> getAll();

    T update(T o);

    boolean delete(Object id);
}
