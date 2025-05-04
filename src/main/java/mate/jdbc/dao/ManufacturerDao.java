package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufacturer);

    boolean delete(Long id);

    Optional<Manufacturer> get(Long id);

    Manufacturer update(Manufacturer manufacturer);
}
