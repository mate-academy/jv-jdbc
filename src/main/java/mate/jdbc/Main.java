package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer.toString());
        }
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(2L);
        System.out.printf(optionalManufacturer.toString());
  /*      Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Peugeot");
        manufacturer.setCountry("France");
        System.out.println(manufacturerDao.create(manufacturer));
        manufacturerDao.delete(manufacturer.getId()); */
    //    manufacturerDao.update(new Manufacturer(1L, "Honda", "Japan"));
        // test other methods from ManufacturerDao
    }
}
