package mate.jdbc.dao;

import mate.jdbc.model.Manufacturer;

import java.util.Optional;

public interface ManufacturerDao {
    public Manufacturer create(Manufacturer manufacturer);

    public Optional<Manufacturer> get(Long id);
}
