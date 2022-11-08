package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDaoImpl)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerAudi = new Manufacturer();
        manufacturerAudi.setName("Audi");
        manufacturerAudi.setCountry("Germany");
        manufacturerDao.create(manufacturerAudi);
        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(all);
        Optional<Manufacturer> manufacturer = manufacturerDao.get(1L);
        if (manufacturer.isPresent()) {
            Manufacturer manufacturer1 = manufacturer.get();
            System.out.println(manufacturer1);
        }
        boolean isDeleted = manufacturerDao.delete(1L);
        System.out.println(isDeleted);
    }
}
