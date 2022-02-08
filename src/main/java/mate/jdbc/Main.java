package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
/*
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Renault");
        manufacturer.setCountry("France");
        manufacturerDao.create(manufacturer);
 */
/*
        boolean result = manufacturerDao.delete(2L);
        System.out.println("Method delete returned " + result);
*/
/*
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Totota");
        manufacturer.setCountry("Japan");
        manufacturer.setId(2L);
        Manufacturer returnedManuf = manufacturerDao.update(manufacturer);
        System.out.println(returnedManuf);
*/
/*
        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(5L);
        manufacturerOptional.ifPresent(System.out::println);
*/
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
