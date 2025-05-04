package mate.jdbc.lib;

import java.util.List;
import java.util.Optional;
import mate.jdbc.models.Manufacturer;

public interface ManufacturerDao {
    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufacturer);

    boolean delete(long id);

    Optional<Manufacturer> get(long id);

    Manufacturer update(Manufacturer manufacturer);
}
