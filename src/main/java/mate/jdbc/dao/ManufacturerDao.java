package mate.jdbc.dao;

import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;

@Dao
public interface ManufacturerDao {

    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);

    /*List<LiteraryFormat> getAll();

    LiteraryFormat create(LiteraryFormat format);

    boolean delete(Long id);*/
}
