package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                         .getInstance(ManufacturerDao.class);
        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes");
        mercedes.setCountry("Germany");
        manufacturerDao.create(mercedes);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(mercedes.getId()));
        Manufacturer hyundai = new Manufacturer();
        hyundai.setName("Hyundai");
        hyundai.setCountry("South Korea");
        manufacturerDao.create(hyundai);
        hyundai.setName("BMW");
        hyundai.setCountry("Germany");
        manufacturerDao.update(hyundai);
        manufacturerDao.delete(hyundai.getId());
    }
}
