package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.db.Manufacturer;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacture);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
