package mate.jdbc.dao;

import mate.jdbc.models.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacturer);

    List<Manufacturer> getAll();

    Optional<Manufacturer> get(Long id);

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
