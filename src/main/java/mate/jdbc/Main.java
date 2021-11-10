package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println("Result of get method: " + manufacturerDao.get(1L));
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Porsche");
        manufacturer.setCountry("Germany");
        System.out.println("Result of create method: " + manufacturerDao.create(manufacturer));
        System.out.println("Result of getAll method: " + manufacturerDao.getAll());
        manufacturer.setCountry("Germany");
        System.out.println("Result of update method: " + manufacturerDao.update(manufacturer));
        System.out.println("Result of delete method: " + manufacturerDao.delete(3L));
    }
}
