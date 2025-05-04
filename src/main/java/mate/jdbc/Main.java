package mate.jdbc;

import java.util.List;
import mate.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");
    private static final int INDEX_TO_GET_MANUFACTURER = 2;
    private static final int INDEX_TO_DELETE = 1;
    private static final int INDEX_OF_MANUFACTURER_TO_UPDATE = 2;
    private static final String NEW_NAME_TO_UPDATE = "Volkswagen G.A.";

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
        List<Manufacturer> manufacturersWithIds = manufacturerDao.getAll();
        System.out.println("manufacturerDao.getAll() = "
                + manufacturersWithIds);
        long idToGetManufacturer = manufacturersWithIds.get(INDEX_TO_GET_MANUFACTURER).getId();
        System.out.println("manufacturerDao.get(" + idToGetManufacturer + ") = "
                + manufacturerDao.get(idToGetManufacturer));
        Manufacturer manufacturerToUpdate =
                manufacturersWithIds.get(INDEX_OF_MANUFACTURER_TO_UPDATE);
        manufacturerToUpdate.setName(NEW_NAME_TO_UPDATE);
        System.out.println(
                "manufacturerDao.update(manufacturers.get("
                        + INDEX_OF_MANUFACTURER_TO_UPDATE + ")) = "
                        + manufacturerDao.update(manufacturerToUpdate));
        long idToDelete = manufacturersWithIds.get(INDEX_TO_DELETE).getId();
        System.out.println("manufacturerDao.delete(" + idToDelete + ") = "
                + manufacturerDao.delete(idToDelete));
    }
}
