package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerMercedes = new Manufacturer();
        manufacturerMercedes.setId(manufacturerMercedes.getId());
        manufacturerMercedes.setName("Toyota");
        manufacturerMercedes.setCountry("Japan");
        Manufacturer manufacturerFord = new Manufacturer();
        manufacturerFord.setId(manufacturerFord.getId());
        manufacturerFord.setName("Citroen");
        manufacturerFord.setCountry("France");
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturerMercedes);
        manufacturerDao.create(manufacturerFord);
        manufacturerDao.delete(manufacturerMercedes.getId());
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(manufacturerFord.getId()));
        System.out.println(manufacturerDao.get(manufacturerMercedes.getId()));
        manufacturerDao.update(manufacturerMercedes);
        manufacturerDao.update(manufacturerFord);
    }
}
