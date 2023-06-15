package mate.jdbc.service;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class ManufactureServiceImpl implements ManufacturerService {
    private final ManufacturerDao manufacturerDao;

    public ManufactureServiceImpl(ManufacturerDao manufacturerDao) {
        this.manufacturerDao = manufacturerDao;
    }

    @Override
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Optional<Manufacturer> getManufacturer(Long id) {
        return manufacturerDao.get(id);
    }

    @Override
    public List<Manufacturer> getAllManufacturer() {
        return manufacturerDao.getAll();
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        return manufacturerDao.update(manufacturer);
    }

    @Override
    public boolean deleteManufacturer(Long id) {
        return manufacturerDao.delete(id);
    }
}
