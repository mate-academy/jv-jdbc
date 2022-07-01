package dao;

import exceptions.DataProcessingException;
import java.util.List;
import java.util.Optional;

import mate.jdbc.lib.Dao;
import model.Manufacturer;

@Dao
public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll() throws DataProcessingException;

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
