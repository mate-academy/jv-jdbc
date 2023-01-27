package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        // initialize field values using setters or constructor
        Manufacturer manufacturerToCreate = new Manufacturer();
        manufacturerToCreate.setName("Luka");
        manufacturerToCreate.setCountry("Slovenia");
        Manufacturer manufacturerToUpdate = new Manufacturer();
        manufacturerToUpdate.setName("perez");
        manufacturerToUpdate.setCountry("Bulgaria");
        manufacturerToUpdate.setId(2L);
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(3L);
        Manufacturer manufacturer = manufacturerDao.create(manufacturerToCreate);
        Manufacturer updated = manufacturerDao.update(manufacturerToUpdate);
        boolean isDeleted = manufacturerDao.delete(4L);
        // test other methods from ManufacturerDao
    }
}
