package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDaoImpl) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerFord = new Manufacturer("Ford", "USA");
        manufacturerDao.create(manufacturerFord);
        System.out.println(manufacturerDao.get(4L));
        Manufacturer manufacturerAudi = new Manufacturer(2L,"Audi", "Germany");
        manufacturerDao.update(manufacturerAudi);
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.getAll());
    }
}
