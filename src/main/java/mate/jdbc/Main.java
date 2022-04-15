package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.model.dao.ManufacturerDao;
import mate.jdbc.model.entity.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer hxtm = new Manufacturer();
        hxtm.setName("Bucephalus").setCountry("Ukraine");
        Manufacturer rheinmetall = new Manufacturer();
        rheinmetall.setName("Marder").setCountry("Germany");
        manufacturerDao.create(hxtm);
        manufacturerDao.create(rheinmetall);
        System.out.println(hxtm);
        System.out.println(rheinmetall);
        manufacturerDao.delete(rheinmetall.getId());
        manufacturerDao.getAll().forEach(System.out::println);
        hxtm.setName("BucePhalus").setName("UKR");
        manufacturerDao.update(hxtm);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}