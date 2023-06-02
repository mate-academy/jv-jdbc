package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.models.ManufacturerGenerator;

public class Main {
    private static final Injector injector
            = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        ManufacturerGenerator manufacturerGenerator = new ManufacturerGenerator();
        List<Manufacturer> manufacturers = manufacturerGenerator.addManufacturers();

        manufacturers.forEach(manufacturerDao::create);
        System.out.println("The Manufacturer table has been supplemented "
                + "with the following values: "
                + manufacturers + "\n");

        Manufacturer manufacturerToUpdate = manufacturers.get(2);
        manufacturerToUpdate.setName("Mercedes");
        manufacturerToUpdate.setCountry("Germany");
        manufacturerDao.update(manufacturerToUpdate);
        System.out.println("The Manufacturer table has been updated "
                + "with the following values: "
                + manufacturers + "\n");

        Manufacturer manufacturerToDelete = manufacturers.get(2);
        System.out.println("Manufacturer deleted: "
                + manufacturerDao.delete(manufacturerToDelete.getId()) + "\n");

        System.out.println("Updated manufacturer table: "
                + manufacturerDao.getAll());
    }
}
