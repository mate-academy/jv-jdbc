package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer bmw = new Manufacturer("BMW","Germany");
        System.out.println("Create manufacture with name=BMW, country=Germany: "
                + manufacturerDao.create(bmw));;
        Manufacturer fiat = new Manufacturer("Fiat","Italy");
        System.out.println("Create manufacture with name=Fiat, country=Italy: "
                + manufacturerDao.create(fiat));;
        System.out.println("Get manufacturer with id=1: " + manufacturerDao
                .get(fiat.getId()).toString());;
        System.out.println("Get all manufacturers: " + manufacturerDao.getAll().toString());;
        bmw.setName("Suzuki");
        bmw.setCountry("Japan");
        System.out.println("Update manufacturer with name=Suzuki, country=Japan: "
                + manufacturerDao.update(bmw).toString());;
        System.out.println("Delete manufacturer with id=1: "
                + manufacturerDao.delete(fiat.getId()));

    }
}
