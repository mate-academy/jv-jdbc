package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer.Builder manufacturerBuilder = new Manufacturer.Builder();
        Manufacturer manufacturer =
                manufacturerBuilder.setName("BMW").setCountry("Germany").build();
        System.out.println(manufacturerDao.create(manufacturer));
    }
}
