package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Sweden");
        manufacturer.setName("Volvo");
        manufacturerDao.create(manufacturer);
        manufacturer.setCountry("France");
        manufacturer.setName("Peugeot");
        manufacturerDao.create(manufacturer);
        manufacturer.setCountry("Germany");
        manufacturer.setName("BMV");
        manufacturerDao.create(manufacturer);
        manufacturer.setCountry("Germany");
        manufacturer.setName("Audi");
        manufacturerDao.create(manufacturer);
        manufacturer.setCountry("Italy");
        manufacturer.setName("Maserati");
        manufacturerDao.create(manufacturer);
        manufacturer.setCountry("Usa");
        manufacturer.setName("Ford");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Mercedes-Benz");
        manufacturer.setId(6L);
        manufacturerDao.update(manufacturer);
        manufacturer.setName("Tesla");
        manufacturer.setCountry("USA");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(7L));
        manufacturerDao.delete(6L);
        manufacturerDao.getAll().stream().forEach(System.out::println);
        // initialize field values using setters or constructor
        // test other methods from ManufacturerDao
    }
}
