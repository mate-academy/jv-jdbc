package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Volkswagen");
        manufacturer1.setCountry("Germany");

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Toyota");
        manufacturer2.setCountry("Japan");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        manufacturer1 = manufacturerDao.create(manufacturer1);
        manufacturer2 = manufacturerDao.create(manufacturer2);

        System.out.println(manufacturerDao.getAll());

        manufacturer1.setCountry("USA");

        manufacturerDao.update(manufacturer1);

        System.out.println(manufacturerDao.get(manufacturer1.getId()));

        manufacturerDao.delete(manufacturer2.getId());

        System.out.println(manufacturerDao.getAll());
    }
}
