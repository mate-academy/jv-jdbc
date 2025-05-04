package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer nike = new Manufacturer();
        nike.setName("Nike");
        nike.setCountry("USA");

        Manufacturer adidas = new Manufacturer();
        adidas.setName("Adidas");
        adidas.setCountry("Island");

        Manufacturer puma = new Manufacturer();
        puma.setName("Puma");
        puma.setCountry("England");

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(nike);
        manufacturerDao.create(adidas);
        manufacturerDao.create(puma);

        System.out.println(manufacturerDao.get(1L));

        manufacturerDao.getAll().forEach(System.out::println);

        puma.setCountry("Iran");
        manufacturerDao.update(puma);

        manufacturerDao.delete(adidas.getId());
    }
}
