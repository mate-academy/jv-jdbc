package mate.jdbc.dao;

import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerDao {
    List<Manufacturer> getAll();

    Optional<Manufacturer> get (Long id);

    Manufacturer create(Manufacturer manufacturer);

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
