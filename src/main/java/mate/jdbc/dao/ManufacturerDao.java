package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacturer);

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll();
}
