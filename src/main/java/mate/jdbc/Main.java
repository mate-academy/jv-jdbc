package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Shevrole");
        manufacturer.setCountry("USA");
        manufacturerDao.create(manufacturer);
        Optional<Manufacturer> manufacturer1 = manufacturerDao.get(7L);
        manufacturerDao.delete(8L);
        manufacturer.setId(10L);
        manufacturer.setName("Ferari");
        manufacturer.setCountry("Italy");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
