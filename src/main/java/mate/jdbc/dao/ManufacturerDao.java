package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufaturer);

    Optional<Manufacturer> get(Long id);

    Manufacturer update(Manufacturer manufaturer);

    boolean delete(Long id);
}
