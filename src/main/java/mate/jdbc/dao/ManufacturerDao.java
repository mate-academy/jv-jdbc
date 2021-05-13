package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    @Dao
    Manufacturer create(Manufacturer manufacturer);

    @Dao
    Optional<Manufacturer> get(Long id);

    @Dao
    List<Manufacturer> getAll();

    @Dao
    Manufacturer update(Manufacturer manufacturer);

    @Dao
    boolean delete(Long id);
}
