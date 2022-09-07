package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final String PACKAGE = "mate.jdbc";
    private static final Injector injector = Injector.getInstance(PACKAGE);

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        //manufacturer.create(Manufacturer manufacturer)
        Manufacturer corn = new Manufacturer("CornIndustry", "Ukraine");
        manufacturerDao.create(corn);

        Manufacturer bavovna = new Manufacturer("BavovnaHimarsovna", "USA/Ukraine");
        manufacturerDao.create(bavovna);

        //manufacturer.getAll()
        List<Manufacturer> listOfManufacturers = manufacturerDao.getAll();
        listOfManufacturers.forEach(System.out::println);
        System.out.println();

        //manufacturer.delete(Long id)
        System.out.println("Corn manufacturer was \"deleted\"");
        manufacturerDao.delete(corn.getId());
        listOfManufacturers = manufacturerDao.getAll();
        listOfManufacturers.forEach(System.out::println);
        System.out.println();

        //manufacturer.update(Manufacturer manufacturer)
        System.out.println("Bavovna country was updated");
        bavovna.setCountry("Ukraine");
        manufacturerDao.update(bavovna);
        List<Manufacturer> updatedListOfManufacturers = manufacturerDao.getAll();
        updatedListOfManufacturers.forEach(System.out::println);
        System.out.println();

        //manufacturer.get(Long id)
        System.out.println("Show me the best bavovna in Crimea");
        if (manufacturerDao.get(14L).isPresent()) {
            System.out.println(manufacturerDao.get(14L));
        }
        System.out.println();
    }
}
