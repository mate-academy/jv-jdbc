package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer zazUkraine = new Manufacturer();
        zazUkraine.setCountry("Ukraine");
        zazUkraine.setName("ZAZ");
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        Manufacturer ford = new Manufacturer();
        ford.setCountry("USA");
        ford.setName("FORD");
        List<Manufacturer> manufacturerList = new ArrayList<>();
        manufacturerList.add(zazUkraine);
        manufacturerList.add(bmw);
        manufacturerList.add(ford);
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        for (Manufacturer manufacturer: manufacturerList) {
            manufacturerDao.create(manufacturer);
        }
        System.out.println(manufacturerDao.getAll());
        zazUkraine.setCountry("Ukraine - One Love");
        zazUkraine.setName("ZazUkraine");
        manufacturerDao.update(zazUkraine);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(zazUkraine.getId());
        System.out.println(manufacturerDao.getAll());
        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(bmw.getId());
        System.out.println(manufacturerOptional);
    }
}
