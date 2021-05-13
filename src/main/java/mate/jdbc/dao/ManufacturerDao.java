package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;

@Dao
public interface ManufacturerDao {
    Optional<Manufacturer> read(Long id);

    List<Manufacturer> readAll();

    Manufacturer create(Manufacturer manufacturer);

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
