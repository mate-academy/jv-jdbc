package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        
        List<Manufacturer> manufacturers = getManufacturers();
        for (Manufacturer manufacturer : manufacturers) {
            manufacturerDao.create(manufacturer);
        }

        Optional<Manufacturer> toyota =
                manufacturerDao.get(manufacturers.get(manufacturers.size() - 1).getId());
        System.out.println(toyota.get());
        System.out.println();

        toyota.get().setCountry("Ukraine");
        Manufacturer updatedToyota = manufacturerDao.update(toyota.get());
        System.out.println(updatedToyota);
        System.out.println();

        manufacturerDao.delete(2L);

        manufacturerDao.getAll().forEach(System.out::println);
    }

    private static List<Manufacturer> getManufacturers() {
        Manufacturer nissan = new Manufacturer();
        nissan.setName("Nissan");
        nissan.setCountry("Japan");
        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        Manufacturer subaru = new Manufacturer();
        subaru.setName("Subaru");
        subaru.setCountry("Japan");

        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(nissan);
        manufacturers.add(toyota);
        manufacturers.add(subaru);
        return manufacturers;
    }
}
