package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDaoImpl) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerOne = new Manufacturer("DeLorean DMC-12, 1981", "USA");
        Manufacturer manufacturerTwo = new Manufacturer("Nissan Leopard, 1980", "Japan");
        manufacturerDao.create(manufacturerOne);
        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(1L);
        manufacturerDao.create(manufacturerTwo);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println(manufacturers);
        Manufacturer manufacturerThree = new Manufacturer(1L,"Mercedes-Benz 126, 1987", "Germany");
        manufacturerDao.update(manufacturerThree);
        System.out.println(manufacturers);
        boolean delete = manufacturerDao.delete(1L);
        System.out.println(delete);
    }
}
