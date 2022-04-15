package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturerOfSocks = new Manufacturer();
        manufacturerOfSocks.setName("Zhytomyr Socks");
        manufacturerOfSocks.setCountry("Ukraine");
        Manufacturer manufacturerOfJewelery = new Manufacturer();
        manufacturerOfJewelery.setName("Artigiano");
        manufacturerOfJewelery.setCountry("Ukraine");

        Manufacturer createdZhytomyrManufacturer = manufacturerDao.create(manufacturerOfSocks);
        Manufacturer createdArtigianoManufacturer = manufacturerDao.create(manufacturerOfJewelery);

        boolean zhytomyrIsDeleted = manufacturerDao.delete(manufacturerOfSocks.getId());

        manufacturerOfJewelery.setName("IndianArtigiano");
        manufacturerOfJewelery.setCountry("India");
        Manufacturer updatedJeweleryManufacturer = manufacturerDao.update(manufacturerOfJewelery);
        System.out.println(updatedJeweleryManufacturer);

        Optional<Manufacturer> optionalManufacturerById = manufacturerDao
                .get(manufacturerOfJewelery.getId());
        System.out.println(optionalManufacturerById.get());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
