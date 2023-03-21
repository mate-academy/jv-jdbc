package mate.jdbc.dao;

import java.util.List;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    List<Manufacturer> getAll();
}
