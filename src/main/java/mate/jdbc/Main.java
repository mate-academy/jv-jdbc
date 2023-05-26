package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerAudi = createRecordManufacturer(
                manufacturerDao, "Audi", "Germany");
        Manufacturer manufacturerChrysler = createRecordManufacturer(
                manufacturerDao, "Chrysler", "USA");

        System.out.println(manufacturerChrysler);

        manufacturerAudi.setName("Buick");
        manufacturerAudi.setCountry("USA");
        Manufacturer manufacturerBuick = manufacturerDao.update(manufacturerAudi);
        System.out.println(manufacturerBuick);

        Manufacturer manufacturerVW = createRecordManufacturer(
                manufacturerDao, "Volkswagen", "Germany");
        Manufacturer copyManufacturerVW = manufacturerDao.get(manufacturerVW.getId()).get();
        System.out.println(copyManufacturerVW);

        Manufacturer manufacturerRenault = createRecordManufacturer(
                manufacturerDao, "Renault", "France");
        manufacturerDao.delete(manufacturerRenault.getId());

        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        System.out.println(manufacturerList);
    }

    private static Manufacturer createRecordManufacturer(
            ManufacturerDao manufacturerDao, String name, String country) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturerDao.create(manufacturer);
    }
}

