package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T create(T o);

    Optional<T> get(Long id);

    List<T> getAll();

    T update(T o);

    boolean delete(Long id);
}
