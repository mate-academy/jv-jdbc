package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer toyotaManufacturer = new Manufacturer("Toyota", "Japan");
        Manufacturer fordManufacturer = new Manufacturer("Ford", "USA");

        manufacturerDao.create(toyotaManufacturer);
        manufacturerDao.create(fordManufacturer);

        var manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }

        System.out.println(manufacturerDao.get(fordManufacturer.getId()));

        fordManufacturer.setName("RebrandingFord");
        manufacturerDao.update(fordManufacturer);

        manufacturerDao.delete(fordManufacturer.getId());

        manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }

        boolean isDeleted = manufacturerDao.delete(toyotaManufacturer.getId());
        System.out.println(isDeleted);
    }
}
