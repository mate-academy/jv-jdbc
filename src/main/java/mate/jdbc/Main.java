package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer hyundai = new Manufacturer("HYUNDAI Motor Company", "South Korea");

        manufacturerDao.create(hyundai);

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println("List from DB:");
        manufacturers.stream().forEach(System.out::println);

        Manufacturer mercedes = new Manufacturer(1L, "Mercedes Benz", "Germany");
        mercedes.setCountry("China");
        mercedes.setName("ChinaStyle");
        System.out.println("Updated manufacturer to " + manufacturerDao.update(mercedes));

        manufacturers = manufacturerDao.getAll();
        System.out.println("List with changes:");
        manufacturers.stream().forEach(System.out::println);

        Manufacturer kia = new Manufacturer(3L, "KIA Motors", "South Korea");
        System.out.println(manufacturerDao.delete(kia.getId()));
        System.out.println("Get MERCEDES: " + manufacturerDao.get(mercedes.getId()).orElse(null));
        System.out.println("Get deleted KIA: " + manufacturerDao.get(kia.getId()).orElse(null));
        System.out.println("Get wrong id: " + manufacturerDao.get(55L).orElse(null));
    }
}
