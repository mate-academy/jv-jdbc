package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        manufacturerDao.create(toyota);
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        manufacturerDao.create(bmw);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.get(2L));
        Manufacturer volvo = new Manufacturer();
        volvo.setName("Volvo");
        volvo.setCountry("Swedish");
        volvo.setId(2L);
        manufacturerDao.update(volvo);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}

