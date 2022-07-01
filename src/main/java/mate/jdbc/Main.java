package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        //test getAll()
        System.out.println("getAll():" + System.lineSeparator());
        manufacturerDao.getAll().forEach(System.out::println);

        //test create()
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Opel");
        manufacturer.setCountry("Germany");
        System.out.println(System.lineSeparator() + "Trying add new:" + System.lineSeparator());
        System.out.println(manufacturerDao.create(manufacturer));

        //test update()
        System.out.println(System.lineSeparator() + "Trying update 3rd:" + System.lineSeparator());
        manufacturer.setId(3L);
        manufacturer.setName("Ferrari");
        manufacturer.setCountry("Italy");
        System.out.println(manufacturerDao.update(manufacturer));

        //test get(id)
        System.out.println(System.lineSeparator()
                + "Trying to get by index 4: " + manufacturerDao.get(4L).get());

        long idDeleted = 4L;
        //test delete(id)
        System.out.println(System.lineSeparator() + "Trying to delete 4th: "
                + manufacturerDao.delete(idDeleted) + System.lineSeparator());

        System.out.println("Getting all after changes:" + System.lineSeparator());
        manufacturerDao.getAll().forEach(System.out::println);

        //test update() after deleting
        System.out.println(System.lineSeparator() + "Trying update 4th (after it was deleted):");
        manufacturer.setId(4L);
        try {
            manufacturerDao.update(manufacturer);

        } catch (RuntimeException e) {
            System.out.println("As expected - RuntimeException if updating already deleted");
        }

        //test get(id) after deleting
        System.out.println(System.lineSeparator() + "Trying get 4th (after it was deleted):");
        if (manufacturerDao.get(4L).isEmpty()) {
            System.out.println(System.lineSeparator()
                    + "As expected - Optional.empty() if getting deleted");
        }
    }
}
