package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T, K> {
    T create(T t);

    Optional<T> get(K k);

    List<T> getAll();

    T update(T t);

    boolean delete(K k);
}
