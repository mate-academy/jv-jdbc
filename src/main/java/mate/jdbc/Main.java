package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Ukraine");
        Manufacturer saveManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(saveManufacturer + " create method");
        Optional<Manufacturer> saveManufacturerOptional =
                manufacturerDao.get(saveManufacturer.getId());
        System.out.println(saveManufacturerOptional.get() + " get method");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println(allManufacturers + " getAll method");
        manufacturer.setCountry("Germany");
        manufacturerDao.update(manufacturer);
        allManufacturers = manufacturerDao.getAll();
        System.out.println(allManufacturers + " update method");
        manufacturerDao.delete(manufacturer.getId());
        allManufacturers = manufacturerDao.getAll();
        System.out.println(allManufacturers + " delete method");
    }
}
