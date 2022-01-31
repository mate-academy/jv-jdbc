package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("German");

        Manufacturer hyundai = new Manufacturer();
        hyundai.setName("Hyundai");
        hyundai.setCountry("South Korea");

        Manufacturer kia = new Manufacturer();
        kia.setName("Kia");
        kia.setCountry("South Korea");

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(audi);
        manufacturerDao.create(hyundai);
        manufacturerDao.create(kia);

        manufacturerDao.delete(6L);
        System.out.println(manufacturerDao.get(5L));

        Manufacturer volkswagen = new Manufacturer();
        volkswagen.setId(1L);
        volkswagen.setName("Volkswagen");
        volkswagen.setCountry("German");
        manufacturerDao.update(volkswagen);

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
