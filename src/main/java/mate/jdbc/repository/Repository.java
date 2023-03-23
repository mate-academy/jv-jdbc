package mate.jdbc.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    T create(T manufacturer);

    Optional<T> get(Long id);

    List<T> getAll();

    Optional<T> update(T manufacturer);

    boolean delete(Long id);
}
