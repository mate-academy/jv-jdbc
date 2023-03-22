package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao dao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        dao.create(new Manufacturer("Audi", "Germany"));
        dao.create(new Manufacturer("Not established", "Not established"));
        dao.update(new Manufacturer(2L, "Mitsubishi", "Japan"));
        dao.delete(1L);
        dao.get(2L);
        System.out.println(dao.getAll());
    }
}
