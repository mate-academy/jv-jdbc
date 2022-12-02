package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        final Manufacturer.Builder germanManufacturerBuilder =
                new Manufacturer.Builder().setCountry("Germany");
        final Manufacturer.Builder franceManufacturerBuilder =
                new Manufacturer.Builder().setCountry("France");

        manufacturerDao.create(germanManufacturerBuilder.setName("Mercedes").build());
        manufacturerDao.create(germanManufacturerBuilder.setName("BMW").build());

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer: manufacturers) {
            System.out.println(manufacturer.toString());
        }

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(3L));

        System.out.println(manufacturerDao
                .update(germanManufacturerBuilder.setId(1L).setName("Opel").build()));
        System.out.println(manufacturerDao
                .update(franceManufacturerBuilder.setId(2L).setName("Renault").build()));

        manufacturerDao.delete(1L);
        manufacturerDao.delete(2L);
    }
}
