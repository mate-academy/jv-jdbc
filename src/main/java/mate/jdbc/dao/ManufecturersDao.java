package mate.jdbc.dao;

import mate.jdbc.model.Manufacturer;

import java.util.List;

public interface ManufecturersDao {
    Manufacturer create(Manufacturer manufacturer);

    boolean delete(Long id);

    Manufacturer get(Long id);

    List<Manufacturer> getAll();

    boolean update(Long id, Manufacturer manufacturer);
}
