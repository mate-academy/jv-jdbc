package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

        Manufacturer.Builder manufacturerBuilder = new Manufacturer.Builder();
        manufacturerDao
                .create(manufacturerBuilder.setName("Mercedes").setCountry("Germany").build());
        manufacturerDao
                .create(manufacturerBuilder.setName("BMW").setCountry("Germany").build());

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer: manufacturers) {
            System.out.println(manufacturer.toString());
        }

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(3L));

        System.out.println(manufacturerDao
                .update(manufacturerBuilder.setId(1L).setName("Opel").setCountry("Germany")
                .build()));
        System.out.println(manufacturerDao
                .update(manufacturerBuilder.setId(2L).setName("Renault").setCountry("France")
                .build()));
        manufacturerDao.delete(1L);
        manufacturerDao.delete(2L);
    }
}
