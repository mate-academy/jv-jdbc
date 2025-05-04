package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer mazda = new Manufacturer();
        mazda.setName("Mazda");
        mazda.setCountry("Japan");
        System.out.println("Creating new manufacturer Bugatti " + manufacturerDao.create(mazda));
        Manufacturer citroen = new Manufacturer();
        citroen.setCountry("France");
        citroen.setName("Citroën");
        manufacturerDao.create(citroen);
        System.out.println("Getting one of existing manufacturers from DB "
                + manufacturerDao.get(mazda.getId()));
        System.out.println("Deleting all one of existing manufacturers from DB "
                + manufacturerDao.delete(citroen.getId()));
        Manufacturer lexus = new Manufacturer();
        lexus.setId(20L);
        lexus.setName("Lexus");
        lexus.setCountry("Japan");
        System.out.println("Updating existing manufacturer with id 10 "
                + manufacturerDao.update(lexus));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
