package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer skoda = new Manufacturer();
        skoda.setName("Skoda");
        skoda.setCountry("Сhezh");
        Manufacturer manufacturerSkoda = manufacturerDao.create(skoda);
        manufacturerDao.delete(manufacturerSkoda.getId());
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer mercedes = new Manufacturer("Mercedes", "Germany");
        manufacturerDao.create(bmw);
        manufacturerDao.create(mercedes);
        System.out.println(manufacturerDao.getAll());
    }
}
