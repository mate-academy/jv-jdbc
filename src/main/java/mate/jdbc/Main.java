package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) Injector.getInstance("mate.jdbc")
                        .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(2L));
        Manufacturer ford = new Manufacturer("Ford", "USA");
        System.out.println(manufacturerDao.create(ford));
        System.out.println(manufacturerDao.delete(1L));
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        audi.setId(4L);
        System.out.println(manufacturerDao.update(audi));
    }
}
