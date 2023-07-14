package mate.jdbc.dao;

import java.util.List;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;


public interface ManufacturerDao {
    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufacturer);

    boolean delete(Long id);

    Manufacturer getById(Long id);

    boolean updateManufacturer(Manufacturer manufacturer);
}
