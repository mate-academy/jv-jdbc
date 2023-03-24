package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao MANUFACTURER_DAO =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("Goga");
        firstManufacturer.setCountry("Ukraine");
        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setName("Goofy");
        secondManufacturer.setCountry("Gendalf");
        MANUFACTURER_DAO.create(firstManufacturer);
        MANUFACTURER_DAO.create(secondManufacturer);
        List<Manufacturer> allManufacturersList = MANUFACTURER_DAO.getAll();
        for (Manufacturer manufacturer : allManufacturersList) {
            System.out.println(manufacturer);
        }
        System.out.println(MANUFACTURER_DAO.get(firstManufacturer.getId()));
        secondManufacturer.setName("upadtedname");
        System.out.println(MANUFACTURER_DAO.update(secondManufacturer));
        System.out.println(MANUFACTURER_DAO.delete(firstManufacturer.getId()));;
        System.out.println(MANUFACTURER_DAO.getAll());
    }
}
