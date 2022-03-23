package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        Manufacturer volvo = manufacturerDao.create(new Manufacturer("Volvo","Switzerland"));
        Manufacturer mercedes = manufacturerDao.create(new Manufacturer("Mercedes-Benz","Germany"));
        Manufacturer bmw = manufacturerDao.create(new Manufacturer("BMW","Japan"));
        Manufacturer trs = manufacturerDao.create(new Manufacturer("TRS","France"));
        System.out.println(manufacturerDao.get(1L).get().getName());
        bmw = manufacturerDao.update(new Manufacturer(3L,"BMW","Germany"));
        manufacturerDao.delete(4L);
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        for (Manufacturer manufacturers : allManufacturer) {
            System.out.println(manufacturers.getName());
        }
    }
}
