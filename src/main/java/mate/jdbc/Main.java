package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();

        manufacturer.setName("Volkswagen");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);

        manufacturer.setName("Mitsubishi");
        manufacturer.setCountry("Japan");
        manufacturerDao.create(manufacturer);

        manufacturer.setName("Citroen");
        manufacturer.setCountry("France");
        manufacturerDao.create(manufacturer);

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));

        manufacturer.setId(1L);
        manufacturer.setName("Opel");
        manufacturer.setCountry("Germany");
        manufacturerDao.update(manufacturer);

        manufacturerDao.delete(3L);

        System.out.println(manufacturerDao.getAll());
    }
}
