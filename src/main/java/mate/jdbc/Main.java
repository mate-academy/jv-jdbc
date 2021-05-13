package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer chocolate = new Manufacturer("Roshen", "Ukraine");
        Manufacturer vine = new Manufacturer("Villa Krym", "Ukraine");
        manufacturerDao.create(chocolate);
        manufacturerDao.create(vine);
        manufacturerDao.get(chocolate.getId());
        manufacturerDao.getAll();
        chocolate.setName("Millennium");
        manufacturerDao.update(chocolate);
        manufacturerDao.delete(chocolate.getId());
    }
}
