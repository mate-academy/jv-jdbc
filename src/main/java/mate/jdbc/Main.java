package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector
            = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Peugeot");
        manufacturer1.setCountry("France");

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Volkswagen");
        manufacturer2.setCountry("German");

        Manufacturer manufacturer3 = new Manufacturer();
        manufacturer3.setName("Ford");
        manufacturer3.setCountry("USA");

        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(manufacturer1.getId()));
        System.out.println(manufacturerDao.delete(manufacturer2.getId()));
        manufacturerDao.getAll().forEach(System.out::println);

        manufacturer1.setName("Mitsubishi");
        manufacturer1.setCountry("Japan");
        manufacturerDao.update(manufacturer1);
        System.out.println(manufacturerDao.get(manufacturer1.getId()));
    }
}
