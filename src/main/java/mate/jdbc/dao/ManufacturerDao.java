package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;

import mate.jdbc.models.Manufacturer;

public interface ManufacturerDao {
    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer name);

    boolean delete(Long id);

    Manufacturer update(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);
}
