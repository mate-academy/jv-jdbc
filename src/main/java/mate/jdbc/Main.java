package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Volvo");
        manufacturer.setCountry("Sweden");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
        System.out.println("Add new manufacturer" + savedManufacturer);

        savedManufacturer.setName("Opel");
        savedManufacturer.setCountry("Germany");
        Manufacturer updatedManufacturer = manufacturerDao.update(savedManufacturer);
        System.out.println("Update new manufacturer" + updatedManufacturer);

        System.out.println("Get All manufacturers" + manufacturerDao.getAll());

        manufacturerDao.delete(updatedManufacturer.getId());
        System.out.println("Delete manufacturer by id " + updatedManufacturer.getId()
                + " " + manufacturerDao.getAll());

        System.out.println(manufacturerDao.get(45L));

    }
}
