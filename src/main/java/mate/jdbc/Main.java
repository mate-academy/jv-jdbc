package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer manufacturerBmw = new Manufacturer();
        manufacturerBmw.setName("BMW");
        manufacturerBmw.setCountry("Germany");

        Manufacturer manufacturerFord = new Manufacturer();
        manufacturerFord.setName("Ford");
        manufacturerFord.setCountry("USA");

        Manufacturer manufacturerLincoln = new Manufacturer();
        manufacturerLincoln.setName("Lincoln");
        manufacturerLincoln.setCountry("USA");

        Manufacturer manufacturerVw = new Manufacturer();
        manufacturerVw.setId(1L);
        manufacturerVw.setName("VW");
        manufacturerVw.setCountry("Germany");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(manufacturerBmw);
        manufacturerDao.create(manufacturerFord);
        manufacturerDao.create(manufacturerLincoln);
        System.out.println(manufacturerDao.get(1L).get());
        manufacturerDao.update(manufacturerVw);
        manufacturerDao.delete(2L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
