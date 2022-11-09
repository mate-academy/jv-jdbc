package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface GeneralDao<T, K> {
    List<T> getAll();

    T create(T object);

    Optional<T> get(K id);

    T update(T object);

    boolean delete(K id);
}
