package jdbc.hw;

import jdbc.hw.dao.ManufacturerDao;
import jdbc.hw.lib.Injector;
import jdbc.hw.model.Manufacturer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
    private static final Injector injector = Injector.getInstance("jdbc.hw");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer testMf = new Manufacturer();
        testMf.setName("Lexus");
        testMf.setCountry("Japan");

        log.info("Added manufacturer: " + manufacturerDao.create(testMf));

        log.info("Info about manufacturer id: " + testMf.getId()
                + " = " + manufacturerDao.get(testMf.getId()));

        testMf.setName("Kia ");
        testMf.setCountry("Korea");
        testMf.setId(5L);

        log.info("Updated manufacturer with id: " + testMf.getId()
                + ". New data: " + manufacturerDao.update(testMf));

        manufacturerDao.getAll().forEach(System.out::println);

        log.info("Deleted manufacturer: [" + manufacturerDao.delete(3L)
                + "] for id: " + testMf.getId());
    }
}
