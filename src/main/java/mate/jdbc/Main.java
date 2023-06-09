package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer zaz = new Manufacturer("ZAZ", "Ukraine");
        Manufacturer hyundai = new Manufacturer("Hyundai", "Korea");
        Manufacturer volvo = new Manufacturer("Volvo", "Sweden");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        manufacturerDao.create(zaz);
        manufacturerDao.create(hyundai);
        manufacturerDao.create(volvo);
        manufacturerDao.create(bmw);
        manufacturerDao.getAll()
                .stream()
                .forEach(System.out::println);
        manufacturerDao.delete(bmw.getId());
        manufacturerDao.getAll()
                .stream()
                .forEach(System.out::println);
    }
}
