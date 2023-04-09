package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final Manufacturer manufacturer = new Manufacturer();
    private static final String GERMANY_COUNTRY = "Germany";
    private static final String JAPAN_COUNTRY = "Japan";
    private static final String MANUFACTURER_AUDI = "Audi";
    private static final String MANUFACTURER_TOYOTA = "Toyota";
    private static final Long INDEX_9 = 9L;

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        createManufacturer(MANUFACTURER_TOYOTA, JAPAN_COUNTRY, manufacturerDao);
        getManufacturerByIndex(INDEX_9, manufacturerDao);
        updateManufacturer(MANUFACTURER_AUDI, GERMANY_COUNTRY, INDEX_9, manufacturerDao);
        deleteManufacturer(INDEX_9, manufacturerDao);
        deleteAllManufacturers(manufacturerDao);
        manufacturerDao.getAll().forEach(System.out::println);
    }

    private static Manufacturer createManufacturer(String name, String country,
                                                   ManufacturerDao manufacturerDao) {
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturerDao.create(manufacturer);
    }

    private static void deleteAllManufacturers(ManufacturerDao manufacturerDao) {
        for (Manufacturer iterator : manufacturerDao.getAll()) {
            manufacturerDao.delete(iterator.getId());
        }
    }

    private static Optional<Manufacturer> getManufacturerByIndex(Long id,
                                                                 ManufacturerDao manufacturerDao) {
        return manufacturerDao.get(id);
    }

    private static void deleteManufacturer(Long id, ManufacturerDao manufacturerDao) {
        manufacturerDao.delete(id);
    }

    private static void updateManufacturer(String nameToUpdate, String countryToUpdate,
                               Long idToUpdate, ManufacturerDao manufacturerDao) {
        manufacturer.setName(nameToUpdate);
        manufacturer.setCountry(countryToUpdate);
        manufacturer.setId(idToUpdate);
        manufacturerDao.update(manufacturer);
    }
}
