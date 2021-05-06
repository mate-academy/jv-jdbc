package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.truncate();

        Manufacturer bmwManufacturer = new Manufacturer();
        bmwManufacturer.setCountry("Germany");
        bmwManufacturer.setName("BMW");

        Manufacturer mercedesManufacturer = new Manufacturer();
        mercedesManufacturer.setCountry("Germany");
        mercedesManufacturer.setName("Mercedes");

        Manufacturer toyotaManufacturer = new Manufacturer();
        toyotaManufacturer.setCountry("Japan");
        toyotaManufacturer.setName("Toyota");

        System.out.println(manufacturerDao.create(bmwManufacturer));
        System.out.println(manufacturerDao.create(mercedesManufacturer));
        System.out.println(manufacturerDao.create(toyotaManufacturer));

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));
        System.out.println(manufacturerDao.get(100L));

        mercedesManufacturer.setName("Mercedes Benz");
        System.out.println(manufacturerDao.update(mercedesManufacturer));

        System.out.println(manufacturerDao.delete(3L));

        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(all);
    }
}
