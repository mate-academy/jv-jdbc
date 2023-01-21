package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerFirst = new Manufacturer();
        Manufacturer manufacturerSecond = new Manufacturer();
        manufacturerFirst.setName("audi");
        manufacturerFirst.setCountry("germany");
        manufacturerSecond.setName("mazda");
        manufacturerSecond.setCountry("japan");
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer createManufacturerFirst = manufacturerDao.create(manufacturerFirst);
        Manufacturer createManufacturerSecond = manufacturerDao.create(manufacturerSecond);
        Manufacturer updateManufacturerFirst = manufacturerDao.update(createManufacturerFirst);
        Manufacturer updateManufacturerSecond = manufacturerDao.update(createManufacturerSecond);
        manufacturerDao.delete(updateManufacturerSecond.getId());
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
    }
}
