package mate.jdbc.service;

import java.util.List;
import mate.jdbc.model.Manufacturer;

public interface ManufacturerService {
    Manufacturer createManufacturer(Manufacturer manufacturer);

    Manufacturer getManufacturerById(Long id);

    List<Manufacturer> getAllManufacturers();

    Manufacturer updateManufacturer(Manufacturer manufacturer);

    boolean deleteManufacturer(Long id);
}
