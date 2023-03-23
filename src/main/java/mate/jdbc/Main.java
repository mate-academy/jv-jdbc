package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturedDao;
import mate.jdbc.dao.ManufacturedDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Long MANUFACTURER_ID = 7L;
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturedDao manufacturedDao =
            (ManufacturedDaoImpl) injector.getInstance(ManufacturedDao.class);

    public static void main(String[] args) {
        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("Goga");
        firstManufacturer.setCountry("Ukraine");
        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setName("Goofy");
        secondManufacturer.setCountry("Gendalf");
        manufacturedDao.create(firstManufacturer);
        manufacturedDao.create(secondManufacturer);
        List<Manufacturer> allManufacturersList = manufacturedDao.getAll();
        for (Manufacturer manufacturer : allManufacturersList) {
            System.out.println(manufacturer);
        }
        System.out.println(manufacturedDao.get(MANUFACTURER_ID));
        secondManufacturer.setName("upadtedname");
        System.out.println(manufacturedDao.update(secondManufacturer));
        System.out.println(manufacturedDao.delete(MANUFACTURER_ID));;
        System.out.println(manufacturedDao.getAll());
    }
}
