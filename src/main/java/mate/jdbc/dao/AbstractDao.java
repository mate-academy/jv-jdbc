package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> {

    Optional<T> get(long id);

    List<T> getAll();

    T create(T t);

    T update(T t);

    boolean delete(long t);
}
