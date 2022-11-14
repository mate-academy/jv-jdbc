package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer mazda = new Manufacturer("Mazda", "Japan");
        Manufacturer volvo = new Manufacturer("Volvo", "Volvo");

        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(bmw);
        manufacturerDao.create(volvo);
        manufacturerDao.create(mazda);
        manufacturerDao.delete(3L);
        System.out.println(manufacturerDao.get(2l));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
