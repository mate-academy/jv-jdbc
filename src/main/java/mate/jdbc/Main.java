package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

        System.out.println("Delete second row");
        manufacturerDao.delete(2L);

        System.out.println("Get all manufacturers");
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("Add new row");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("new manufacturer");
        manufacturer.setCountry("Ukraine");
        System.out.println("New manufacturer id = " + manufacturerDao.create(manufacturer));

        System.out.println("Update manufacturer");
        manufacturer.setName("updated manufacturer");
        System.out.println(manufacturerDao.update(manufacturer));

        System.out.println("Get manufacturer");
        System.out.println(manufacturerDao.get(3L).get());
    }
}
