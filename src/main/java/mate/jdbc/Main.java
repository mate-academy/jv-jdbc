package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate");
    private static final long FIND_ID = 3;
    private static final long REMOVE_ID = 5;
    
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) INJECTOR.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Audi");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);

        manufacturer.setName("Citroen");
        manufacturer.setCountry("France");
        manufacturerDao.update(manufacturer);

        Optional<Manufacturer> retrievedManufacturer = manufacturerDao.get(FIND_ID);
        if (retrievedManufacturer.isPresent()) {
            System.out.println(retrievedManufacturer.get().getId());
        }

        System.out.println(manufacturerDao.delete(REMOVE_ID));

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println(manufacturers.size());
    }
}
