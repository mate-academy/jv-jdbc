package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer honda = new Manufacturer("Honda", "Japan");
        manufacturerDao.create(honda);
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        manufacturerDao.create(bmw);
        Manufacturer updateHonda = manufacturerDao.get(honda.getId()).get();
        updateHonda.setCountry("USA");
        manufacturerDao.update(updateHonda);
        System.out.println(manufacturerDao.getAll());
    }
}
