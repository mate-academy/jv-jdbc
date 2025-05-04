package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<E, K> {
    E create(E e);

    Optional<E> get(K id);

    List<E> getAll();

    E update(E e);

    boolean delete(K id);
}
