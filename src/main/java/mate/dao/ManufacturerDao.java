package mate.dao;

import java.util.List;
import java.util.Optional;
import mate.domain.Manufacturer;

public interface ManufacturerDao {
    public Manufacturer create(Manufacturer manufacturer);

    public Optional<Manufacturer> get(Long id);

    public List<Manufacturer> getAll();

    public Manufacturer update(Manufacturer manufacturer);

    public boolean delete(Long id);
}
