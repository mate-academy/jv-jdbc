package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao dao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        dao.create(toyota);

        Manufacturer mercedesBenz = new Manufacturer();
        mercedesBenz.setName("Mercedes Benz");
        mercedesBenz.setCountry("Germany");
        dao.create(mercedesBenz);

        Manufacturer ferrari = new Manufacturer();
        ferrari.setName("Ferrari");
        ferrari.setCountry("Italia");
        dao.create(ferrari);

        System.out.println("Created new manufacturers");
        dao.getAll().forEach(System.out::println);

        System.out.println("Get manufacturer with id = " + toyota.getId());
        System.out.println(dao.get(toyota.getId()));

        System.out.println(dao.delete(mercedesBenz.getId())
                ? "manufacturer(id = " + mercedesBenz.getId() + ") is deleted" : "can't delete");
        dao.getAll().forEach(System.out::println);

        Manufacturer bmw = new Manufacturer();
        bmw.setId(ferrari.getId());
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        dao.update(bmw);
        System.out.println("Update manufacturer with id = " + ferrari.getId());
        dao.getAll().forEach(System.out::println);
    }
}
