package mate.jdbc.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll() throws SQLException;

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
