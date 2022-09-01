package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ConnectionUtil.getConnection();
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer kia = new Manufacturer(null, "KIA", "Korea");
        Manufacturer hyundai = new Manufacturer(null, "Hyundai", "USA");
        Manufacturer mercedes = new Manufacturer(null, "Mercedes", "Germany");
        kia = manufacturerDao.create(kia);
        hyundai = manufacturerDao.create(hyundai);
        mercedes = manufacturerDao.create(mercedes);
        System.out.println("\n=== Added 3 manufacturers ===\n");

        manufacturerDao.getAll().forEach(System.out::println);

        hyundai.setCountry("Italia");
        manufacturerDao.update(hyundai);
        System.out.println("\n=== Updated Hyundai manufacturer ===\n");
        System.out.println(manufacturerDao.get(hyundai.getId()));

        manufacturerDao.delete(mercedes.getId());
        System.out.println("\n=== Deleted Mercedes manufacturer ===\n");

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
