package mate.jdbc.lib;

import java.util.List;
import java.util.Optional;
import mate.jdbc.models.Manufacturer;

public interface ManufacturerDao {

    Manufacturer create(Manufacturer manufacturer);

    List<Manufacturer> getAll();

    Optional<Manufacturer> get(Long id);

    Optional<Manufacturer> update(Manufacturer manufacturer);

    boolean delete(Long id);
}
