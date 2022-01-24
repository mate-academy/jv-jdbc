package mate.jdbc;

import mate.jdbc.dao.manufacturerdao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final String PATH_IMPLEMENTATION = "mate.jdbc";
    private static final Injector injector = Injector.getInstance(PATH_IMPLEMENTATION);

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerOne = new Manufacturer("JMC","USA");
        Manufacturer manufacturerTwo = new Manufacturer("Honda","Japan");
        Manufacturer manufacturerThree = new Manufacturer("Fiat","Italy");
        Manufacturer manufacturerFour = new Manufacturer("BMW","Germany");
        Manufacturer manufacturerFive = new Manufacturer("Volvo","Sweden");
        manufacturerDao.create(manufacturerOne);
        manufacturerDao.create(manufacturerTwo);
        manufacturerDao.create(manufacturerThree);
        manufacturerDao.create(manufacturerFour);
        manufacturerDao.create(manufacturerFive);
        System.out.println(manufacturerDao.get(2L));
        manufacturerDao.delete(4L);
        Manufacturer manufacturerUpdate = new Manufacturer("Lamborghini","Italy");
        manufacturerUpdate.setId(3L);
        manufacturerDao.update(manufacturerUpdate);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
