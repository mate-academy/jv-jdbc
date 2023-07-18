package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.models.Manufacturer;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacturer) throws DataProcessingException;

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll() throws DataProcessingException;

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
