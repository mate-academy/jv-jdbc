package mate.jdbc.dao;

import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import java.util.List;
import java.util.Optional;

@Dao
public interface ManufacturersDao {

    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);

    boolean delete(Long id);

    Manufacturer update(Manufacturer manufacturer);
}
