package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDaoImpl manufacturerDao = (ManufacturerDaoImpl) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer fordManufacturer = new Manufacturer();
        fordManufacturer.setName("Ford");
        fordManufacturer.setCountry("USA");
        Manufacturer dodgeManufacturer = new Manufacturer();
        dodgeManufacturer.setName("Dodge");
        dodgeManufacturer.setCountry("USA");
        //create
        manufacturerDao.create(fordManufacturer);
        manufacturerDao.create(dodgeManufacturer);
        //get all
        manufacturerDao.getAll().forEach(System.out::println);
        //get by index
        System.out.println(manufacturerDao.get(dodgeManufacturer.getId()));
        //update
        System.out.println(manufacturerDao.update(dodgeManufacturer));
        //delete
        manufacturerDao.delete(7L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
