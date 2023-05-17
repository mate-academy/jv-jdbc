package mate.jdbc;

import java.sql.SQLException;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) throws SQLException {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bmv = new Manufacturer("BMW", "Germany");
        Manufacturer toyota = new Manufacturer("Toyota", "France");
        Manufacturer hyundai = new Manufacturer("Hyundai", "USA");

        Manufacturer savedBmv = manufacturerDao.create(bmv);
        Manufacturer savedToyota = manufacturerDao.create(toyota);
        Manufacturer savedHyundai = manufacturerDao.create(hyundai);

        savedToyota.setCountry("USA");
        Manufacturer updatedToyota = manufacturerDao.update(savedToyota);
        Manufacturer bmvFromDB = manufacturerDao.get(savedBmv.getId()).get();
        manufacturerDao.delete(savedHyundai.getId());

        System.out.println(manufacturerDao.getAll());
    }
}
