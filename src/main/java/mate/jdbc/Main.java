package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerList().forEach(manufacturerDao::create);
        manufacturerDao.update(new Manufacturer(1L,"Ford", "USA"));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.delete(1L));
        System.out.println(manufacturerDao.get(2L));
    }

    private static List<Manufacturer> manufacturerList() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(new Manufacturer("Fo", "USA"));
        manufacturers.add(new Manufacturer("Chevrolet", "USA"));
        manufacturers.add(new Manufacturer("Dodge", "USA"));
        manufacturers.add(new Manufacturer("Jeep", "USA"));
        manufacturers.add(new Manufacturer("Cadillac", "USA"));
        manufacturers.add(new Manufacturer("Chrysler", "USA"));
        manufacturers.add(new Manufacturer("Hummer", "USA"));
        manufacturers.add(new Manufacturer("GMC", "USA"));
        manufacturers.add(new Manufacturer("Lincoln", "USA"));
        manufacturers.add(new Manufacturer("Audi", "Germany"));
        manufacturers.add(new Manufacturer("Porsche", "Germany"));
        manufacturers.add(new Manufacturer("BMW", "Germany"));
        manufacturers.add(new Manufacturer("Opel", "Germany"));
        manufacturers.add(new Manufacturer("Mercedes-Benz", "Germany"));
        manufacturers.add(new Manufacturer("Maybach", "Germany"));
        manufacturers.add(new Manufacturer("Acura", "Japan"));
        manufacturers.add(new Manufacturer("Infiniti", "Japan"));
        manufacturers.add(new Manufacturer("Honda", "Japan"));
        manufacturers.add(new Manufacturer("Lexus", "Japan"));
        manufacturers.add(new Manufacturer("Mitsubishi", "Japan"));
        manufacturers.add(new Manufacturer("Mazda", "Japan"));
        manufacturers.add(new Manufacturer("Nissan", "Japan"));
        manufacturers.add(new Manufacturer("Suzuki", "Japan"));
        manufacturers.add(new Manufacturer("Subaru", "Japan"));
        manufacturers.add(new Manufacturer("Toyota", "Japan"));
        return manufacturers;
    }
}
