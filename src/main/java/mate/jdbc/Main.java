package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer renault = new Manufacturer("Renault", "France");
        Manufacturer shevrolet = new Manufacturer("Shevrolet", "USA");
        Manufacturer audi = new Manufacturer("Audi", "USA");
        manufacturerDao.create(renault);
        manufacturerDao.create(shevrolet);
        Manufacturer createdManufacturer = manufacturerDao.create(audi);

        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("List of created manufacturers\n");

        System.out.println(manufacturerDao.get(createdManufacturer.getId()).get());
        System.out.println("Manufacturer which was got by ID\n");

        Manufacturer updatedManufacturer = new Manufacturer(createdManufacturer.getId(),
                createdManufacturer.getName(), "Germany");
        System.out.println(manufacturerDao.update(updatedManufacturer));
        System.out.println("Manufacturer which was updated\n");

        manufacturerDao.delete(updatedManufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("List of manufacturers without deleted one\n");

    }
}
