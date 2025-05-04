package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer scoda = new Manufacturer();
        scoda.setName("Scoda");
        scoda.setCountry("Czech Republic");

        Manufacturer volkswagen = new Manufacturer();
        volkswagen.setName("Volkswagen");
        volkswagen.setCountry("Germany");

        Manufacturer bugatti = new Manufacturer();
        bugatti.setName("Bugatti");
        bugatti.setCountry("France");

        Manufacturer porsche = new Manufacturer();
        porsche.setName("Porsche");
        porsche.setCountry("Germany");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println("Testing is table empty");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("_______________________________");

        manufacturerDao.create(scoda);
        manufacturerDao.create(volkswagen);
        manufacturerDao.create(bugatti);
        manufacturerDao.create(porsche);

        System.out.println("Testing create and read (get by id)");
        System.out.println(manufacturerDao.get(scoda.getId()));
        System.out.println(manufacturerDao.get(volkswagen.getId()));
        System.out.println(manufacturerDao.get(bugatti.getId()));
        System.out.println(manufacturerDao.get(porsche.getId()));
        System.out.println(manufacturerDao.get(25L));
        System.out.println("_______________________________");

        bugatti.setName("Bugatti Automobiles");
        manufacturerDao.update(bugatti);
        manufacturerDao.delete(porsche.getId());

        System.out.println("Testing read (get all), update and delete");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
