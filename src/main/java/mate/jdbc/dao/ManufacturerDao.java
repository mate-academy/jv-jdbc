package mate.jdbc.dao;

import mate.jdbc.Model.Manufacturer;
import java.util.List;
import java.util.Optional;

public interface ManufacturerDao {
    Manufacturer create (Manufacturer manufacturer);
    Optional<Manufacturer> get (Long id);
    Manufacturer update (Manufacturer manufacturer);
    boolean delete (Long id);
    List<Manufacturer> getAll();
}
