package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("BMW");
        manufacturer1.setCountry("Germany");

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Volkswagen");
        manufacturer2.setCountry("Germany");

        Manufacturer manufacturer3 = new Manufacturer();
        manufacturer3.setName("Subaru");
        manufacturer3.setCountry("Japan");

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);

        manufacturerDao.delete(10L);
        System.out.println(manufacturerDao.get(10L));
        System.out.println(manufacturerDao.get(9L));

        Manufacturer manufacturer4 = new Manufacturer();
        manufacturer4.setId(9L);
        manufacturer4.setName("Peugeot");
        manufacturer4.setCountry("France");
        manufacturerDao.update(manufacturer4);

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
