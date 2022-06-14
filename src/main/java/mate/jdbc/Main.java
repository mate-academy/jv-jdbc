package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("KIA");
        manufacturer.setCountry("Kanada");
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Tesla");
        manufacturer1.setCountry("USA");
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Zaporozec");
        manufacturer2.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);//++
        manufacturerDao.create(manufacturer1);//++
        manufacturerDao.create(manufacturer2);//++
        Optional<Manufacturer> manufacturer3 = manufacturerDao.get(3L);//++
        Optional<Manufacturer> manufacturer4 = manufacturerDao.get(4L);//++
        Optional<Manufacturer> manufacturer6 = manufacturerDao.get(6L);//++
        System.out.println(manufacturer3.orElseGet(Manufacturer::new));
        System.out.println(manufacturer4.orElseGet(Manufacturer::new));
        System.out.println(manufacturer6.orElseGet(Manufacturer::new));
        List<Manufacturer> all = manufacturerDao.getAll();//++
        for (Manufacturer m : all) {
            System.out.println(m);
        }
        Manufacturer manufacturer5 = new Manufacturer();
        manufacturer5.setName("Volvo");
        manufacturer5.setCountry("Nigeria");
        manufacturer5.setId(5L);
        manufacturerDao.update(manufacturer5);//++
        manufacturerDao.delete(3L);
    }
}
