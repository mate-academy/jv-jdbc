package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");

        Manufacturer mazda = new Manufacturer();
        mazda.setName("Mazda");
        mazda.setCountry("Japan");

        Manufacturer volvo = new Manufacturer();
        volvo.setName("Volvo");
        volvo.setCountry("Sweden");

        manufacturerDao.create(bmw);
        manufacturerDao.create(volvo);
        manufacturerDao.create(mazda);

        manufacturerDao.delete(1l);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
