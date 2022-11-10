package mate.jdbc.dao;

import mate.jdbc.models.Manufacturer;

import java.util.List;
public interface ManufacturerDao {
    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer name);

    boolean delete(Long id);
    Manufacturer update(Manufacturer manufacturer);
}
