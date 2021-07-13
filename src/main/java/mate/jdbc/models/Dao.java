package mate.jdbc.models;

import java.util.List;
import java.util.Optional;

public interface Dao<O> {
    O create(O object);

    Optional<O> get(Long id);

    List<O> getAll();

    Optional<O> update(O object);

    boolean delete(Long idOfSomething);
}
