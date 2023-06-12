package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDaoImpl manufacturerDao = (ManufacturerDaoImpl) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer volksvagenManufacturer = new Manufacturer();
        Manufacturer toyotaManufacturer = new Manufacturer();
        volksvagenManufacturer.setName("Volksvagen");
        volksvagenManufacturer.setCountry("Germany");
        toyotaManufacturer.setName("Toyota");
        toyotaManufacturer.setCountry("Japan");
        manufacturerDao.create(volksvagenManufacturer);
        manufacturerDao.create(toyotaManufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(volksvagenManufacturer.getId()));
        System.out.println(manufacturerDao.update(volksvagenManufacturer));
        manufacturerDao.delete(2L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
