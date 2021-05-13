package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao
                .class);
        Manufacturer peugeotManufacturer = new Manufacturer("Peugeot", "France");
        Manufacturer bmwManufacturer = new Manufacturer("BMW", "Germany");
        Manufacturer toyotaManufacturer = new Manufacturer("Toyota", "Japan");

        manufacturerDao.create(peugeotManufacturer);
        manufacturerDao.create(bmwManufacturer);
        manufacturerDao.create(toyotaManufacturer);

        System.out.println(manufacturerDao.get(bmwManufacturer.getId()));

        Manufacturer mazdaManufacturer = new Manufacturer(peugeotManufacturer.getId(),
                "Mazda", "Japan");
        manufacturerDao.update(mazdaManufacturer);

        manufacturerDao.delete(bmwManufacturer.getId());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
