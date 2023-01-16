package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer volvo = new Manufacturer();
        volvo.setName("Volvo");
        volvo.setCountry("China");
        System.out.println("Creating new manufacturer Volvo" + manufacturerDao.create(volvo));
        System.out.println("Getting one of existing manufacturers from DB "
                + manufacturerDao.get(3L));
        System.out.println("Deleting all one of existing manufacturers from DB "
                + manufacturerDao.delete(8L));
        Manufacturer manufacturerUpdate = new Manufacturer();
        manufacturerUpdate.setId(10L);
        manufacturerUpdate.setName("Seat");
        manufacturerUpdate.setCountry("France");
        System.out.println("Updating existing manufacturer with id 10 "
                + manufacturerDao.update(manufacturerUpdate));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
