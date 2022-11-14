package mate.jdbc;

import java.util.List;
import mate.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");
    private static final long ID_TO_GET_MANUFACTURER = 2L;
    private static final long ID_TO_SET_UPDATED = 257L;
    private static final long ID_TO_DELETE = 1L;
    private static final int INDEX_OF_MANUFACTURER_TO_UPDATE = 0;

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("Volkswagen", "Germany"),
                new Manufacturer("Daimler", "UK"),
                new Manufacturer("General Motors", "USA"),
                new Manufacturer("Toyota", "Japan")
        );
        for (Manufacturer manufacturer : manufacturers) {
            manufacturerDao.create(manufacturer);
        }
        System.out.println("manufacturerDao.getAll() = "
                + manufacturerDao.getAll());
        System.out.println("manufacturerDao.get(" + ID_TO_GET_MANUFACTURER + ") = "
                + manufacturerDao.get(ID_TO_GET_MANUFACTURER));
        manufacturers.get(INDEX_OF_MANUFACTURER_TO_UPDATE).setId(ID_TO_SET_UPDATED);
        System.out.println(
                "manufacturerDao.update(manufacturers.get("
                        + INDEX_OF_MANUFACTURER_TO_UPDATE + ")) = "
                        + manufacturerDao.update(
                                manufacturers.get(INDEX_OF_MANUFACTURER_TO_UPDATE)));
        System.out.println("manufacturerDao.delete(" + ID_TO_DELETE + ") = "
                + manufacturerDao.delete(ID_TO_DELETE));
    }
}
