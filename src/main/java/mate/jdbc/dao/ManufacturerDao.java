package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> getById(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
