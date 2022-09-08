package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        Manufacturer savedManufacturer = manufacturerDao.create(bmw);
        System.out.println(savedManufacturer);

        Manufacturer opel = new Manufacturer(null, "Opel", "Germany");
        manufacturerDao.create(opel);

        Manufacturer volvo = new Manufacturer(null, "Volvo", "France");
        manufacturerDao.create(volvo);

        Manufacturer zaporozhets = new Manufacturer(null, "Zaporozhets", "Ukraine");
        manufacturerDao.create(zaporozhets);

        manufacturerDao.getAll().forEach(System.out::println);

        volvo.setCountry("Ukraine");
        manufacturerDao.update(volvo);

        System.out.println(manufacturerDao.get(volvo.getId()));

        manufacturerDao.delete(zaporozhets.getId());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
