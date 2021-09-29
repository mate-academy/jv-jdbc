package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println("The result of the method getAll() from ManufacturerDaoImpl Class: ");
        manufacturerDao.getAll().stream()
                .forEach(System.out::println);

        Optional<Manufacturer> getManufacturerById = manufacturerDao.get(2L);
        System.out.println("The result of the method get() by Id from ManufacturerDaoImpl Class: "
                + getManufacturerById);

        boolean deletedManufacturer = manufacturerDao.delete(getManufacturerById.get().getId());
        System.out.println("The result of the method delete() from ManufacturerDaoImpl Class: "
                + deletedManufacturer);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Toyota");
        manufacturer.setCountry("USA");
        Manufacturer createManufacturer = manufacturerDao.create(manufacturer);
        System.out.println("The result of the method create() from ManufacturerDaoImpl Class: "
                + createManufacturer);

        Manufacturer updateManufacturer = manufacturerDao.update(getManufacturerById.get());
        System.out.println("The result of the method update() by Id "
                + "from ManufacturerDaoImpl Class: " + updateManufacturer);
    }
}
