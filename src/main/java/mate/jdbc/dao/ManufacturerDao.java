package mate.jdbc.dao;

import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    void create(Manufacturer manufacturer);

    void get(Long id);

    void getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
