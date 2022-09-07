package mate.jdbc.lib;

import java.util.List;
import mate.jdbc.models.Manufacturer;

public interface ManufacturerDao {
    List<Manufacturer> getAll();
}
