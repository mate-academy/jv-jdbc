package model.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;

@Dao
public interface MyDao<Domain> {
    Domain create(Domain manufacturer);

    Optional<Domain> get(Long id);

    List<Domain> getAll();

    Domain update(Domain manufacturer);

    boolean delete(Long id);
}
