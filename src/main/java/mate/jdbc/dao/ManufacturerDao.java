package mate.jdbc.dao;

import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerDao {
    Manufacturer update(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufacturer);

    boolean delete(Long id);
}
