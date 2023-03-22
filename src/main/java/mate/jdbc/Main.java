package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerAlice = new Manufacturer();
        manufacturerAlice.setName("Alice");
        manufacturerAlice.setCountry("Turkey");
        manufacturerDao.create(manufacturerAlice);
        Manufacturer manufacturerTom = new Manufacturer();
        manufacturerTom.setName("Tom");
        manufacturerTom.setCountry("England");
        manufacturerDao.create(manufacturerTom);
        Manufacturer manufacturerBill = new Manufacturer();
        manufacturerBill.setName("Bill");
        manufacturerBill.setCountry("Poland");
        manufacturerDao.create(manufacturerBill);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(manufacturerAlice.getId()));
        manufacturerAlice.setCountry("Ukraine");
        manufacturerDao.update(manufacturerAlice);
        manufacturerDao.delete(manufacturerBill.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
