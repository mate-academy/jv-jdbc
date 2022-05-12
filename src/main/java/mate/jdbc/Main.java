package mate.jdbc;

import mate.jdbc.dao.JdbcManufacturerDao;
import mate.jdbc.dao.JdbcManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final Logger logger = LogManager.getLogger(JdbcManufacturerDaoImpl.class);

    public static void main(String[] args) {
        JdbcManufacturerDao jdbcManufacturerDao = (JdbcManufacturerDao) injector
                .getInstance(JdbcManufacturerDao.class);

        Manufacturer manufacturerFirst = new Manufacturer("FirstManufacturer", "FirstCountry");

        Manufacturer manufacturerSecond = new Manufacturer("SecondManufacturer", "SecondCountry");

        Manufacturer manufacturerThird = new Manufacturer("ThirdManufacturer", "ThirdCountry");

        jdbcManufacturerDao.create(manufacturerSecond);
        jdbcManufacturerDao.create(manufacturerFirst);
        jdbcManufacturerDao.create(manufacturerThird);
        System.out.println(jdbcManufacturerDao.getAll());
        System.out.println("__________________________________________________________________");

        manufacturerSecond.setName("otherName");

        jdbcManufacturerDao.update(manufacturerSecond);
        System.out.println(jdbcManufacturerDao.get(manufacturerSecond.getId()));
        System.out.println(jdbcManufacturerDao.getAll());
        System.out.println("__________________________________________________________________");

        jdbcManufacturerDao.delete(manufacturerSecond.getId());
        System.out.println(jdbcManufacturerDao.get(manufacturerSecond.getId()));
        System.out.println(jdbcManufacturerDao.getAll());
        System.out.println("__________________________________________________________________");

        manufacturerSecond.setName("setOneMoreOtherName");
        System.out.println(manufacturerSecond);
        System.out.println(jdbcManufacturerDao.update(manufacturerSecond));
        System.out.println((jdbcManufacturerDao.get(manufacturerSecond.getId())));
        System.out.println(jdbcManufacturerDao.getAll());
    }
}
