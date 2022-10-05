package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;

public class Main {
    private static final Injector daoInjector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) daoInjector.getInstance(ManufacturerDao.class);
        testCreateUpdateAndDelete(manufacturerDao);
        testGetAllAfterDelete(manufacturerDao);
    }

    private static void testCreateUpdateAndDelete(ManufacturerDao manufacturerDao) {
        Manufacturer manufacturer = new Manufacturer(
                null,
                "FirstManufacturer", "FirstCountry"
        );
        manufacturerDao.create(manufacturer);
        Manufacturer foundedManufacturer = manufacturerDao.get(manufacturer.getId()).orElseThrow();
        assert manufacturer.equals(foundedManufacturer);
        manufacturer.setName("UpdatedName");
        manufacturer.setCountry("UpdatedCountry");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);
        foundedManufacturer = manufacturerDao.get(manufacturer.getId()).orElseThrow();
        assert updatedManufacturer.equals(foundedManufacturer);
        assert manufacturerDao.delete(manufacturer.getId());
        assert manufacturerDao.get(manufacturer.getId()).isEmpty();
    }

    private static void testGetAllAfterDelete(ManufacturerDao manufacturerDao) {
        Manufacturer firstManufacturer = new Manufacturer(
                null,
                "FirstManufacturer", "Country"
        );
        Manufacturer secondManufacturer = new Manufacturer(
                null,
                "SecondManufacturer", "Country"
        );
        List<Manufacturer> manufacturers = List.of(firstManufacturer, secondManufacturer);
        manufacturers.forEach(manufacturerDao::create);
        assert manufacturerDao.getAll().equals(manufacturers);
        manufacturerDao.delete(firstManufacturer.getId());
        assert manufacturerDao.getAll().equals(List.of(secondManufacturer));
    }
}
