package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturedDao;
import mate.jdbc.dao.ManufacturedDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Goga");
        manufacturer1.setCountry("Ukraine");
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Goofy");
        manufacturer2.setCountry("Gendalf");
        ManufacturedDao manufacturedDao = new ManufacturedDaoImpl();
        manufacturedDao.create(manufacturer1);
        manufacturedDao.create(manufacturer2);
        List<Manufacturer> all = manufacturedDao.getAll();
        for (Manufacturer manufacturer : all) {
            System.out.println(manufacturer);
        }
        System.out.println(manufacturedDao.get(7L));
        manufacturer2.setName("upadtedname");
        System.out.println(manufacturedDao.update(manufacturer2));
        System.out.println(manufacturedDao.delete(7L));;
        System.out.println(manufacturedDao.getAll());
    }
}
