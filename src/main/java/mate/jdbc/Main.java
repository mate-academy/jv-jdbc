package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Audi");
        manufacturer1.setCountry("German");
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setId((long)4);
        manufacturer2.setName("Mitsubisi");
        manufacturer2.setCountry("Japan");
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        for (Manufacturer i : manufacturerDao.getAll()) {
            System.out.println(i.toString());
        }
        System.out.println(Optional.of(manufacturerDao.create(manufacturer1)));
        System.out.println(manufacturerDao.get((long)2).toString());
        System.out.println(manufacturerDao.update(manufacturer2));
        System.out.println(manufacturerDao.delete((long) 3));
    }
}
