package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer audi = manufacturerDao.create(new Manufacturer("Audi", "Germany"));
        Manufacturer dodge = manufacturerDao.create(new Manufacturer("Dodge", "USA"));
        Manufacturer nissan = manufacturerDao.create(new Manufacturer("Nissan", "Japan"));

        System.out.println("get: " + manufacturerDao.get(audi.getId()));
        System.out.println("delete:" + manufacturerDao.delete(nissan.getId()));
        dodge.setCountry("Canada");
        Manufacturer canadianDodge = manufacturerDao.update(dodge);
        System.out.println("update: " + manufacturerDao.get(canadianDodge.getId()));
        System.out.println("Manufacturers: " + manufacturerDao.getAll());
    }
}
