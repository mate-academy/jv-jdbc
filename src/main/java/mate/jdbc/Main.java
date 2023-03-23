package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");
    private static final ManufacturerDao dao
            = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer firstManufacturer = dao.create(
                new Manufacturer("Audi", "Germany"));
        Manufacturer secondManufacturer = dao.create(
                new Manufacturer("Not established", "Not established"));
        dao.update(
                new Manufacturer(secondManufacturer.getId(), "Mitsubishi", "Japan"));
        dao.delete(firstManufacturer.getId());
        dao.get(secondManufacturer.getId());
        System.out.println(dao.getAll());
    }
}
