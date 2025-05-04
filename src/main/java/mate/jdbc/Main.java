package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        Manufacturer toyota = new Manufacturer();
        toyota.setName("toyota");
        toyota.setCountry("Japan");
        Manufacturer hyundai = new Manufacturer();
        hyundai.setName("hyundai");
        hyundai.setCountry("Japan");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(toyota);
        manufacturerDao.create(hyundai);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
        toyota = manufacturers.get(0);
        toyota.setCountry("Germany");
        manufacturerDao.update(toyota);
        System.out.println(manufacturerDao.get(toyota.getId()));
        manufacturerDao.delete(toyota.getId());
        manufacturerDao.getAll().stream().forEach(System.out::println);
    }
}
