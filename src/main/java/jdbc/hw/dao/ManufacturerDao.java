package jdbc.hw.dao;

import java.util.List;
import java.util.Optional;
import jdbc.hw.model.Manufacturer;

public interface ManufacturerDao {

    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
