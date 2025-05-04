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
        manufacturer = manufacturerDao.create(manufacturer);
        System.out.println("Add new manufacturer" + manufacturer);

        manufacturer.setName("Opel");
        manufacturer.setCountry("Germany");
        manufacturer = manufacturerDao.update(manufacturer);
        System.out.println("Update new manufacturer" + manufacturer);

        System.out.println("Get All manufacturers" + manufacturerDao.getAll());

        System.out.println(manufacturerDao.get(manufacturer.getId()));

        manufacturerDao.delete(manufacturer.getId());
        System.out.println("Delete manufacturer by id " + manufacturer.getId()
                + " " + manufacturerDao.getAll());
    }
}
