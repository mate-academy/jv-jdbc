package mate.jdbc.service;

import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerService {
    Manufacturer createManufacturer(Manufacturer manufacturer);

    Optional<Manufacturer> getManufacturer(Long id);

    List<Manufacturer> getAllManufacturer();

    Manufacturer updateManufacturer(Manufacturer manufacturer);

    boolean deleteManufacturer(Long id);
}
