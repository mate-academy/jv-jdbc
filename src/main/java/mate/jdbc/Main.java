package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        Manufacturer renault = new Manufacturer();
        renault.setName("Renault");
        renault.setCountry("France");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(audi);
        manufacturerDao.create(renault);
        System.out.println(manufacturerDao.get(audi.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
        renault.setCountry("Germany");
        System.out.println(manufacturerDao.update(renault));
        System.out.println(manufacturerDao.delete(audi.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
