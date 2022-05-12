package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer firstTest = new Manufacturer(null, "Brabus", "Germany");
        Manufacturer secondTest = new Manufacturer(null, "Audi", "Germany");
        Manufacturer thirdTest = new Manufacturer(null, "Subaru", "Japan");
        Manufacturer executedFirst = manufacturerDao.create(firstTest);
        Manufacturer executedSecond = manufacturerDao.create(secondTest);
        Manufacturer executedThird = manufacturerDao.create(thirdTest);
        manufacturerDao.delete(executedSecond.getId());
        executedFirst.setName("Mercedes-Benz");
        Manufacturer getThird = manufacturerDao.get(executedThird.getId()).orElseThrow();
        System.out.println(getThird);
        manufacturerDao.update(executedFirst);

        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
    }
}
