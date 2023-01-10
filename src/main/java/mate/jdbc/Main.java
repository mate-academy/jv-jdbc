package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        //System.out.println(manufacturerDao.getAll());
        Manufacturer manufacturer1 = new Manufacturer();
        Manufacturer manufacturer2 = new Manufacturer();
        //manufacturer1.setCountry("Ukraine");
        //manufacturer1.setName("VEPR");
        manufacturer2.setCountry("USA");
        manufacturer2.setName("HIMARS");
        manufacturerDao.create(manufacturer2);
        //System.out.println(manufacturerDao.getAll());
        //manufacturerDao.delete(3L);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(76L));
    }
}
