package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerBmw = new Manufacturer();
        manufacturerBmw.setName("BMW");
        manufacturerBmw.setCountry("Germany");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(1L));
        manufacturerDao.create(manufacturerBmw);
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.getAll());
        Manufacturer manufacturerHyundai = new Manufacturer();
        manufacturerHyundai.setId(3L);
        manufacturerHyundai.setName("Hyundai");
        manufacturerHyundai.setCountry("Korea");
        manufacturerDao.update(manufacturerHyundai);
        System.out.println(manufacturerDao.getAll());
    }
}
