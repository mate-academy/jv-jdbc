package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer volvo = new Manufacturer();
        volvo.setCountry("Sweden");
        volvo.setName("Volvo");
        System.out.println(manufacturerDao.create(volvo));
        volvo.setCountry("China");
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("Mexico");
        System.out.println(manufacturerDao.create(ford));
        Manufacturer updatedFord = manufacturerDao.get(ford.getId()).get();
        updatedFord.setCountry("USA");
        System.out.println(manufacturerDao.update(updatedFord));
        System.out.println(manufacturerDao.get(volvo.getId()));
        System.out.println(manufacturerDao.delete(volvo.getId()));
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
    }
}
