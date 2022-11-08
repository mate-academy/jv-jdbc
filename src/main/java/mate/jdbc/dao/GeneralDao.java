package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface GeneralDao<T> {
    List<T> getAll();

    T create(T object);

    Optional<T> get(Long id);

    T update(T object);

    boolean delete(Long id);
}
