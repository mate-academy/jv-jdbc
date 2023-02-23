package mate.jdbc.service;

import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    Manufacturer createManufacturer(Manufacturer manufacturer);

    Optional<Manufacturer> getManufacturer(Long id);

    List<Manufacturer> getAllManufacturer();

    Manufacturer updateManufacturer(Manufacturer manufacturer);

    boolean deleteManufacturer(Long id);
}
