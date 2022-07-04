package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(new Manufacturer("BMW", "Germany"));
        manufacturerDao.create(new Manufacturer("Ferrari", "Italy"));

        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println(manufacturerDao.delete(manufacturerList.get(3).getId()));

        Manufacturer manufacturer = manufacturerList.get(3);
        manufacturer.setName("Alfa Romeo");
        manufacturerDao.update(manufacturer);

        System.out.println(manufacturerDao.get(manufacturer.getId()).orElse(null));
    }
}
