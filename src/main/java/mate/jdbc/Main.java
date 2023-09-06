package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.DaoManufacturer;
import mate.jdbc.lib.Injector;
import model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerFirst = new Manufacturer();
        Manufacturer manufacturerSecond = new Manufacturer();
        manufacturerFirst.setName("hyundai");
        manufacturerFirst.setCountry("Korea");
        manufacturerSecond.setName("mercedes");
        manufacturerSecond.setCountry("germany");
        DaoManufacturer manufacturerDao = (DaoManufacturer)
                injector.getInstance(DaoManufacturer.class);
        Manufacturer createManufacturerFirst = manufacturerDao.create(manufacturerFirst);
        Manufacturer createManufacturerSecond = manufacturerDao.create(manufacturerSecond);
        Manufacturer updateManufacturerFirst = manufacturerDao.update(createManufacturerFirst);
        Manufacturer updateManufacturerSecond = manufacturerDao.update(createManufacturerSecond);
        manufacturerDao.delete(updateManufacturerSecond.getId());
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

    }
}
