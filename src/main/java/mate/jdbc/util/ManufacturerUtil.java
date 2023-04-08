package mate.jdbc.util;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

/**
 * Default utils for working with BataBase "car_db"
 */
public class ManufacturerUtil {
    private static final Manufacturer manufacturer = new Manufacturer();
    private static final ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

    public static Manufacturer createManufacturer(String name, String country) {
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturerDao.create(manufacturer);
    }

    public static void deleteAllManufacturers() {
        for (Manufacturer iterator : manufacturerDao.getAll()) {
            manufacturerDao.delete(iterator.getId());
        }
    }

    public static Optional<Manufacturer> getManufacturerByIndex(Long id) {
        return manufacturerDao.get(id);
    }

    public static void deleteManufacturer(Long id) {
        manufacturerDao.delete(id);
    }

    public static void updateManufacturer(String nameToUpdate, String countryToUpdate,
                                          Long idToUpdate) {
        manufacturer.setName(nameToUpdate);
        manufacturer.setCountry(countryToUpdate);
        manufacturer.setId(idToUpdate);
        manufacturerDao.update(manufacturer);
    }
}
