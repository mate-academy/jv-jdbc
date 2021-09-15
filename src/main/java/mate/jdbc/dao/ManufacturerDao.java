package mate.jdbc.dao;

import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import java.util.List;

@Dao
public interface ManufacturerDao {
    List<Manufacturer> getAll();
}
