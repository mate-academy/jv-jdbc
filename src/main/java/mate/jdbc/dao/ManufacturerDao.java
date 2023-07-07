package mate.jdbc.dao;

import models.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerDao {

    Manufacturer create(Manufacturer name);
    Optional<Manufacturer> get(Long id);
    List<Manufacturer> getAll();
    Manufacturer update(Manufacturer manufacturer);
    boolean delete(Long id);
}
