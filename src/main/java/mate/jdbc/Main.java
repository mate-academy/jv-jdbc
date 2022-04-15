package mate.jdbc;

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

        Manufacturer bmw = manufacturerDao.get(2L).get();
        System.out.println("The result of the method get() by Id from ManufacturerDaoImpl Class: "
                + bmw);

        boolean deletedManufacturer = manufacturerDao.delete(bmw.getId());
        System.out.println("The result of the method delete() from ManufacturerDaoImpl Class: "
                + deletedManufacturer);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Toyota");
        manufacturer.setCountry("USA");
        Manufacturer createNewToyota = manufacturerDao.create(manufacturer);
        System.out.println("The result of the method create() from ManufacturerDaoImpl Class: "
                + createNewToyota);

        manufacturer.setName("Toyota Motor Corporation");
        Manufacturer updatedToyota = manufacturerDao.update(manufacturer);
        System.out.println("The result of the method update() by Id "
                + "from ManufacturerDaoImpl Class: " + updatedToyota);
    }
}
