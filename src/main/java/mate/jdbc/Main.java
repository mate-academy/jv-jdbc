package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer ferrari = new Manufacturer();
        ferrari.setCountry("Italy");
        ferrari.setName("Ferrari");
        manufacturerDao.create(ferrari);

        Manufacturer lada = new Manufacturer();
        lada.setCountry("USSR");
        lada.setName("Lada");
        manufacturerDao.create(lada);

        Manufacturer mercedes = new Manufacturer();
        mercedes.setCountry("Germany");
        mercedes.setName("Mercedes");
        manufacturerDao.create(mercedes);

        lada.setCountry("Ukraine");
        manufacturerDao.update(lada);

        System.out.println(manufacturerDao.get(3L));
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(2L);

    }
}
