package mate.jdbc.dao.manufacturer;

import java.util.List;
import java.util.Optional;
import mate.jdbc.models.db.models.Manufacturer;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer dbModel);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer dbModel);

    boolean delete(Long id);
}
