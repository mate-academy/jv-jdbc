package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacturer) throws DataFormatException;

    Optional<Manufacturer> get(Long id) throws DataFormatException;

    List<Manufacturer> getAll() throws DataFormatException;

    Manufacturer update(Manufacturer manufacturer) throws DataFormatException;

    boolean delete(Long id) throws DataFormatException;
}
