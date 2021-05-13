package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        System.out.println("Testing create method: ");
        Manufacturer manufacturerTesla = new Manufacturer("Tesla", "USA");
        manufacturerDao.create(manufacturerTesla);
        Manufacturer manufacturerKia = new Manufacturer("Kia", "Korea");
        manufacturerDao.create(manufacturerKia);
        Manufacturer manufacturerDS = new Manufacturer("Citroen", "France");
        manufacturerDao.create(manufacturerDS);
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("Testing get method: ");
        System.out.println(manufacturerDao.get(manufacturerTesla.getId()));
        System.out.println(manufacturerDao.get(manufacturerKia.getId()));
        System.out.println(manufacturerDao.get(manufacturerDS.getId()));

        System.out.println("Testing update method: ");
        manufacturerDS.setName("DS");
        manufacturerDao.update(manufacturerDS);
        manufacturerKia.setCountry("South Korea");
        manufacturerDao.update(manufacturerKia);
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("Testing delete method: ");
        manufacturerDao.delete(manufacturerKia.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
