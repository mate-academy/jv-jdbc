package mate.jdbc.service.impl;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.ManufacturerService;

import java.util.List;

public class ManufacturerServiceImpl implements ManufacturerService {
    ManufacturerDao manufacturerDao;
    @Override
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Manufacturer getManufacturerById(Long id) {
        return null;
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return null;
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean deleteManufacturer(Long id) {
        return false;
    }
}
