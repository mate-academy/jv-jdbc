package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.models.DBModel;

public interface SQLDao<T extends DBModel> {
    T create(T dbModel);

    Optional<T> get(Long id);

    List<T> getAll();

    T update(T dbModel);

    boolean delete(Long id);
}
