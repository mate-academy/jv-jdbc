package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer macBookPro = new Manufacturer();
        macBookPro.setName("MacBook Pro 13");
        macBookPro.setCountry("United States");

        Manufacturer lenovoNoteBook = new Manufacturer();
        lenovoNoteBook.setName("Lenovo IdeaPad Gaming");
        lenovoNoteBook.setCountry("China");

        Manufacturer xiaomiNoteBook = new Manufacturer();
        xiaomiNoteBook.setName("Xiaomi RedmiBook");
        xiaomiNoteBook.setCountry("China");

        System.out.println(".....Save manufacturers to DB.....");
        System.out.printf("MacBook Pro saved to DB %s%n",
                manufacturerDao.create(macBookPro));
        System.out.printf("Lenovo IdeaPad Gaming saved to DB %s%n",
                manufacturerDao.create(lenovoNoteBook));
        System.out.printf("Xiaomi RedmiBook saved to DB %s%n",
                manufacturerDao.create(xiaomiNoteBook));
        System.out.println();

        System.out.println(".....Remove manufacturer from DB.....");
        System.out.println(manufacturerDao.delete(xiaomiNoteBook.getId()));
        System.out.println();

        System.out.println(".....Show all data from DB.....");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println();

        System.out.println(".....Update manufacturer in DB.....");
        macBookPro.setName("MacBook Pro 16");
        System.out.printf("New element in DB %s%n", manufacturerDao.update(macBookPro));
        System.out.println();

        System.out.println("....Get manufacturer from DB.....");
        System.out.println(manufacturerDao.get(macBookPro.getId()));
    }
}
