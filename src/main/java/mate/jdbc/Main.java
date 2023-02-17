package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("BMW","Germany");
        System.out.println("Create manufacture with name=BMW, country=Germany: "
                + manufacturerDao.create(manufacturer));;
        Manufacturer manufacturerSecond = new Manufacturer("Fiat","Italy");
        System.out.println("Create manufacture with name=Fiat, country=Italy: "
                + manufacturerDao.create(manufacturerSecond));;
        System.out.println("Get manufacturer with id=1: " + manufacturerDao.get(1L).toString());;
        System.out.println("Get all manufacturers: " + manufacturerDao.getAll().toString());;
        manufacturer.setName("Suzuki");
        manufacturer.setCountry("Japan");
        System.out.println("Update manufacturer with name=Suzuki, country=Japan: "
                + manufacturerDao.update(manufacturer).toString());;
        System.out.println("Delete manufacturer with id=1: " + manufacturerDao.delete(1L));

    }
}
