package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer("Ford","USA");
        Manufacturer manufacturer2 = new Manufacturer("Chevrolet","USA");
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println();
        Manufacturer manufacturer = manufacturerDao.get(1L).orElse(new Manufacturer());
        manufacturer.setName("BMW");
        manufacturer.setCountry("Germany");
        manufacturerDao.update(manufacturer);
        System.out.println();
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(2L);
        System.out.println();
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
