package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerTesla = new Manufacturer();
        manufacturerTesla.setName("Tesla");
        manufacturerTesla.setCountry("USA");
        manufacturerDao.create(manufacturerTesla);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturerKozak = new Manufacturer();
        manufacturerKozak.setId(1L);
        manufacturerKozak.setName("Kozak");
        manufacturerKozak.setCountry("Ukraine");
        manufacturerDao.update(manufacturerKozak);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.get(1L));
    }
}
