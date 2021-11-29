package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Audi");
        manufacturer1.setCountry("Germany");
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Ford Mustang");
        manufacturer2.setCountry("USA");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);

        manufacturerDao.get(manufacturer2.getId());
        manufacturerDao.getAll().forEach(System.out::println);

        manufacturer2.setName("Ford");
        manufacturerDao.update(manufacturer2);
        System.out.println(manufacturerDao.get(manufacturer2.getId()));
        manufacturerDao.delete(manufacturer1.getId());
    }
}
