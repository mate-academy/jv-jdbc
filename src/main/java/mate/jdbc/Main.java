package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturerToAdd = new Manufacturer("Smart", "Germany");
        Manufacturer addedManufacturerWithId = manufacturerDao.create(manufacturerToAdd);
        System.out.println(manufacturerDao.get(addedManufacturerWithId.getId()) + "\n");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println();
        addedManufacturerWithId.setName("Renault");
        addedManufacturerWithId.setCountry("France");
        manufacturerDao.update(addedManufacturerWithId);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.delete(addedManufacturerWithId.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
