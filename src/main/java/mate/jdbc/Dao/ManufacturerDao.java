package mate.jdbc.Dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    public Manufacturer create(Manufacturer manufacturer);
    Optional<Manufacturer> get(Long id);
    List<Manufacturer> getAll();
    Manufacturer update(Manufacturer manufacturer);
    boolean delete(Long id);
}
