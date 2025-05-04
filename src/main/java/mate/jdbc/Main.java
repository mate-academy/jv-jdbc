package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("Renault");
        newManufacturer.setCountry("France");

        System.out.println("Method create(): "
                + System.lineSeparator()
                + manufacturerDao.create(newManufacturer));

        System.out.println("Method get(): "
                + System.lineSeparator()
                + manufacturerDao.get(newManufacturer.getId()));

        System.out.println("Method getAll(): ");
        manufacturerDao.getAll().forEach(System.out::println);

        newManufacturer.setName("Dodge");
        newManufacturer.setCountry("USA");
        System.out.println("Method update(): "
                + System.lineSeparator()
                + manufacturerDao.update(newManufacturer));

        System.out.println("Method delete(): "
                + System.lineSeparator()
                + manufacturerDao.delete(newManufacturer.getId()));
    }
}
