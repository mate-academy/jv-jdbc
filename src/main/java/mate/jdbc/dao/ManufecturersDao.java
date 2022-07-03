package mate.jdbc.dao;

import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufecturersDao {
    Manufacturer create(Manufacturer manufacturer);

    boolean delete(Long id);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll();

    boolean update(Long id, Manufacturer manufacturer);
}
