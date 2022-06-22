package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(new Manufacturer("name1", "country1"));
        manufacturerDao.create(new Manufacturer("name2", "country2"));

        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println(manufacturerDao.delete(manufacturerList.get(0).getId()));

        Manufacturer manufacturer = manufacturerList.get(1);
        manufacturer.setName("edited name");
        manufacturerDao.update(manufacturer);

        System.out.println(manufacturerDao.get(manufacturer.getId()).orElse(null));
    }
}
