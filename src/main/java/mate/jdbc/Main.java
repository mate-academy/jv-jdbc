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
        Manufacturer manufacturerKia = new Manufacturer();
        manufacturerKia.setName("KIA");
        manufacturerKia.setCountry("Kanada");
        Manufacturer manufacturerTesla = new Manufacturer();
        manufacturerTesla.setName("Tesla");
        manufacturerTesla.setCountry("USA");
        Manufacturer manufacturerZapor = new Manufacturer();
        manufacturerZapor.setName("Zaporozec");
        manufacturerZapor.setCountry("Ukraine");
        manufacturerDao.create(manufacturerKia);//++
        manufacturerDao.create(manufacturerTesla);//++
        manufacturerDao.create(manufacturerZapor);//++
        Optional<Manufacturer> kiaManufacturer = manufacturerDao.get(manufacturerKia.getId());//++
        Optional<Manufacturer> teslaManufacturer =
                manufacturerDao.get(manufacturerTesla.getId());//++
        Optional<Manufacturer> zaporManufacturer =
                manufacturerDao.get(manufacturerZapor.getId());//++
        System.out.println(kiaManufacturer.orElseGet(Manufacturer::new));
        System.out.println(teslaManufacturer.orElseGet(Manufacturer::new));
        System.out.println(zaporManufacturer.orElseGet(Manufacturer::new));
        List<Manufacturer> all = manufacturerDao.getAll();//++
        for (Manufacturer m : all) {
            System.out.println(m);
        }
        Manufacturer manufacturerVolvo = new Manufacturer();
        manufacturerVolvo.setName("Volvo");
        manufacturerVolvo.setCountry("Nigeria");
        manufacturerVolvo.setId(5L);
        manufacturerDao.update(manufacturerVolvo);//++
        manufacturerDao.delete(3L);
    }
}
