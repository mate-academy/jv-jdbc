package mate.jdbc.service;

import mate.jdbc.model.Manufacturer;
import java.util.List;

public interface ManufacturerService {
    Manufacturer createManufacturer(Manufacturer manufacturer);

    Manufacturer getManufacturerById(Long id);

    List<Manufacturer> getAllManufacturers();

    Manufacturer updateManufacturer(Manufacturer manufacturer);

    boolean deleteManufacturer(Long id);
}
