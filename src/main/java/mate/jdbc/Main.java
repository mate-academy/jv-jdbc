package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        //Create
        Manufacturer airbus = new Manufacturer("Airbus","FRA");
        manufacturerDao.create(airbus);
        Manufacturer boeing = new Manufacturer("Boeing","USA");
        manufacturerDao.create(boeing);
        Manufacturer mcDonnellDouglas = new Manufacturer("McDonnell Douglas","USA");
        manufacturerDao.create(mcDonnellDouglas);
        Manufacturer sukhoi = new Manufacturer("Sukhoi", "SUN");
        manufacturerDao.create(sukhoi);
        Manufacturer ilyushin = new Manufacturer("Ilyushin", "SUN");
        manufacturerDao.create(ilyushin);

        //Get
        System.out.println("\nGET");
        System.out.println(manufacturerDao.get(sukhoi.getId()));

        System.out.println("\nGET ALL after CREATE");
        manufacturerDao.getAll().forEach(System.out::println);

        //Update
        mcDonnellDouglas.setName("Boeing");
        sukhoi.setCountry("RUS");
        ilyushin.setCountry("RUS");
        manufacturerDao.update(mcDonnellDouglas);
        manufacturerDao.update(sukhoi);
        manufacturerDao.update(ilyushin);

        System.out.println("\nGET ALL after UPDATE");
        manufacturerDao.getAll().forEach(System.out::println);

        //Delete
        manufacturerDao.delete(mcDonnellDouglas.getId());
        sukhoi.setName("United Aircraft Corporation");
        ilyushin.setName(sukhoi.getName());
        manufacturerDao.update(sukhoi);
        manufacturerDao.update(ilyushin);
        manufacturerDao.delete(ilyushin.getId());

        System.out.println("\nGET ALL after DELETE");
        manufacturerDao.getAll().forEach(System.out::println);

        //Truncate test table
        manufacturerDao.truncateTable();
    }
}
