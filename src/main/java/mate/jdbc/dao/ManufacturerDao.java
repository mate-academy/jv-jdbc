package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

@Dao
public interface ManufacturerDao {

    Manufacturer create(Manufacturer manufacturer);

    List<Manufacturer> getAll();

    Optional<Manufacturer> get(Long id);

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
