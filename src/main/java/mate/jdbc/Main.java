package mate.jdbc;

import java.util.List;
import mate.jdbc.lib.Injector;
import mate.jdbc.lib.ManufacturerDao;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
        Manufacturer manufacturerVolvo = new Manufacturer();
        manufacturerVolvo.setName("Volvo");
        manufacturerVolvo.setCountry("Sweden");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturerVolvo);
        System.out.println(createdManufacturer);
        System.out.println(manufacturerDao.delete(createdManufacturer.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(manufacturerVolvo.getId()));
        Manufacturer manufacturerCitroen = new Manufacturer();
        manufacturerCitroen.setName("Citroen");
        manufacturerCitroen.setCountry("France");
        manufacturerCitroen.setId(manufacturerVolvo.getId());
        System.out.println(manufacturerDao.update(manufacturerCitroen));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
