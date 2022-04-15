package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(new Manufacturer("audi", "Germany"));
        System.out.println(manufacturerDao.get(5L).get());
        Manufacturer audi = manufacturerDao.get(5L).get();
        audi.setName("Audi Plus");
        manufacturerDao.update(audi);
        Manufacturer volkswagen = manufacturerDao.get(4L).get();
        manufacturerDao.delete(volkswagen.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
