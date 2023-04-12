package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.models.Manufacturer;

public interface ManufacturerDao {
    List<Manufacturer> getAll();

    Optional<Manufacturer> get(Long id);

    Manufacturer create(Manufacturer manufacturer);

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
