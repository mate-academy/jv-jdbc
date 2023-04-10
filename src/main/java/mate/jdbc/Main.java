package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Objects;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("Peugeot", "France"),
                new Manufacturer("Renault", "France"),
                new Manufacturer("Citroen", "France"));

        //Create
        for (Manufacturer manufacturer: manufacturers) {
            System.out.println("Created record in DB: "
                    + manufacturerDao.create(manufacturer));
        }

        //Read
        manufacturers = manufacturerDao.getAll();
        System.out.println("Records in DB:");
        manufacturers.forEach(System.out::println);

        //Update
        Manufacturer modified = manufacturerDao.getAll().stream()
                .filter(m -> Objects.equals(m.getName(), "Peugeot"))
                .findFirst()
                .get();

        modified.setCountry("India");
        modified.setName("TATA");
        System.out.println("Updated in db: "
                + manufacturerDao.update(modified));

        //delete
        manufacturers = manufacturerDao.getAll();
        for (Manufacturer m : manufacturers) {
            System.out.println("Deleted from DB: " + manufacturerDao.delete(m.getId()));
        }
    }
}
