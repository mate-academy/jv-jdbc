package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer manufacturerAlex = new Manufacturer("Alex", "USA");
        Manufacturer manufacturerRony = new Manufacturer("Rony", "Ukraine");
        Manufacturer manufacturerJon = new Manufacturer("Jon", "USA");
        manufacturerDao.create(manufacturerAlex);
        manufacturerDao.create(manufacturerRony);
        manufacturerDao.create(manufacturerJon);
        manufacturerDao.delete(manufacturerRony.getId());
        manufacturerAlex.setCountry("UK");
        manufacturerDao.update(manufacturerAlex);
        manufacturerDao.get(manufacturerAlex.getId());
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
