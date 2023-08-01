package mate.jdbc.service.impl;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exceptions.EntityNotFoundException;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.Inject;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.ManufacturerService;
import mate.jdbc.service.ManufacturerValidationService;

@Dao
@Inject
@Log4j2
public class ManufacturerServiceImpl implements ManufacturerService {
    private ManufacturerDao manufacturerDao;
    private ManufacturerValidationService validationService;

    public ManufacturerServiceImpl(ManufacturerDao manufacturerDao,
                                   ManufacturerValidationService validationService) {
        this.manufacturerDao = manufacturerDao;
        this.validationService = validationService;
    }

    @Override
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        // Перевірка валідності даних виробника перед додаванням до бази даних
        validationService.validateBeforeCreate(manufacturer);
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Manufacturer getManufacturerById(Long id) {
        return manufacturerDao.get(id).orElseThrow(() -> new EntityNotFoundException("Couldn't find"
                + " a manufacturer with id: " + id));
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerDao.getAll();
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        validationService.validateBeforeUpdate(manufacturer);
        return manufacturerDao.update(manufacturer);
    }

    @Override
    public boolean deleteManufacturer(Long id) {
        return manufacturerDao.delete(id);
    }
}
