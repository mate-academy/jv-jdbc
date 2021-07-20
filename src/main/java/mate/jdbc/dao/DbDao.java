package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface DbDao<R> {
    R create(R record);

    Optional<R> get(Long id);

    List<R> getAll();

    R update(R record);

    boolean delete(Long id);
}
