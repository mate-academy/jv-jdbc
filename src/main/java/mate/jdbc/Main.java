package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer bmw = manufacturerDao.create(new Manufacturer("BMW", "Germany"));
        Manufacturer honda = manufacturerDao.create(new Manufacturer("Honda", "Japan"));
        Manufacturer ford = manufacturerDao.create(new Manufacturer("Ford", "USA"));
        Manufacturer porsche = manufacturerDao.create(new Manufacturer("Porsche", "Germany"));
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer: allManufacturers) {
            System.out.println(manufacturer);
        }
        Manufacturer updatedFordCountry = manufacturerDao.update(new Manufacturer(ford.getId(),
                ford.getName(), "USAA"));
        boolean deletedHonda = manufacturerDao.delete(honda.getId());
        System.out.println("Deleted Honda -> " + deletedHonda);
        Optional<Manufacturer> gotPorsche = manufacturerDao.get(porsche.getId());
        System.out.println("Got Porsche -> " + gotPorsche);
        System.out.println("Updated Ford Country -> " + updatedFordCountry);

    }
}

