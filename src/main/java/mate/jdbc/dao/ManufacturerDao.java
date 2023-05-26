package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    Optional<Manufacturer> get(long id);

    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufacturer);

    boolean delete(long id);

    Manufacturer update(Manufacturer manufacturer);
}
