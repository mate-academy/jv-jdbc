package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacturer);

    Manufacturer update(Manufacturer manufacturer);

    List<Manufacturer> getAll();

    Optional<Manufacturer> get(Long id);

    boolean delete(Long id);
}
