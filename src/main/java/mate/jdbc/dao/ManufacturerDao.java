package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.models.Manufacturer;

public interface ManufacturerDao {

    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);

    Manufacturer updated(Manufacturer manufacturer);

    boolean deleted(Long id);
}

