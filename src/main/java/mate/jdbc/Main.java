package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer bmwManufacturer = new Manufacturer("BMW", "Germany");
        Manufacturer toyotaManufacturer = new Manufacturer("Toyota", "Japan");
        manufacturerDao.create(bmwManufacturer);
        manufacturerDao.create(toyotaManufacturer);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(bmwManufacturer.getId());
        optionalManufacturer.ifPresent(System.out::println);
        toyotaManufacturer.setName("TOYOTA");
        manufacturerDao.update(toyotaManufacturer);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(bmwManufacturer.getId());
        System.out.println(manufacturerDao.getAll());
    }

}
