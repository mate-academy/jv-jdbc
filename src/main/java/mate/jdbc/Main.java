package mate.jdbc;

import java.util.List;
import java.util.Objects;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.jdbc");
    private static List<Manufacturer> manufacturers = List.of(
            new Manufacturer("Peugeot", "France"),
            new Manufacturer("Renault", "France"),
            new Manufacturer("Citroen", "France"));

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) INJECTOR.getInstance(ManufacturerDao.class);

        for (Manufacturer manufacturer : manufacturers) {
            System.out.println("Created record in DB: "
                    + manufacturerDao.create(manufacturer));
        }

        manufacturers = manufacturerDao.getAll();
        System.out.println(System.lineSeparator() + "Records in DB:");
        manufacturers.forEach(System.out::println);

        Manufacturer modified = manufacturerDao.getAll().stream()
                .filter(m -> Objects.equals(m.getName(), "Peugeot"))
                .findFirst()
                .get();

        modified.setCountry("India");
        modified.setName("TATA");
        System.out.println(System.lineSeparator() + "Updated in db: "
                + manufacturerDao.update(modified) + System.lineSeparator());

        manufacturers = manufacturerDao.getAll();
        for (Manufacturer m : manufacturers) {
            System.out.println("Deleted record from DB: " + manufacturerDao.delete(m.getId()));
        }
    }
}
