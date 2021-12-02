package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        Manufacturer ferrari = new Manufacturer();
        ferrari.setCountry("Italy");
        ferrari.setName("Ferrari");
        manufacturerDao.create(ferrari);

        Manufacturer porsche = new Manufacturer();
        porsche.setCountry("Germany");
        porsche.setName("Porsche");
        manufacturerDao.create(porsche);

        Manufacturer koenigsegg = new Manufacturer();
        koenigsegg.setCountry("Sweden");
        koenigsegg.setName("Koenigsegg");
        manufacturerDao.create(koenigsegg);

        System.out.println(manufacturerDao.get(3L));
        manufacturerDao.getAll().forEach(System.out::println);

        porsche.setCountry("Ukraine");
        manufacturerDao.update(porsche);

        manufacturerDao.getAll().forEach(System.out::println);

        manufacturerDao.delete(2L);

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
