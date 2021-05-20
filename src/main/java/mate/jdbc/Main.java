package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        manufacturerDao.getAll().forEach(System.out::println);

        Manufacturer acerManufacturer = new Manufacturer();
        acerManufacturer.setName("ACER");
        acerManufacturer.setCountry("CHINA");
        System.out.println(manufacturerDao.create(acerManufacturer));

        System.out.println(manufacturerDao.get(acerManufacturer.getId()));

        acerManufacturer.setName("ACER PRO");
        System.out.println(manufacturerDao.update(acerManufacturer));

        System.out.println(manufacturerDao.delete(acerManufacturer.getId()));
    }
}
