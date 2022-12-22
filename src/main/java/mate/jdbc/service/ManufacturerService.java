package mate.jdbc.service;

import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
