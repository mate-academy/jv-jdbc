package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer mercedes = new Manufacturer("Mercedes", "Germany");
        manufacturerDao.create(mercedes);
        Manufacturer ferraris = new Manufacturer("Ferraris", "France");
        manufacturerDao.create(ferraris);
        Manufacturer lada = new Manufacturer("Lada", "USSR");
        manufacturerDao.create(lada);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer ferrari = manufacturerDao.get(ferraris.getId()).get();
        ferrari.setName("Ferrari");
        ferrari.setCountry("Italy");
        manufacturerDao.update(ferrari);
        System.out.println("updated" + manufacturerDao.get(ferrari.getId()));
        manufacturerDao.getAll().forEach(manufacturer -> manufacturerDao
                .delete(manufacturer.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
