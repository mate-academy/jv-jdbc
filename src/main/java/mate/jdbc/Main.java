package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer manufacturer = new Manufacturer("Fiat", "France");
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(manufacturer.getId());
        manufacturer.setCountry("Italy");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll();
        manufacturerDao.delete(manufacturer.getId());
    }
}
