package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final String MAIN_PACKAGE_NAME = "mate.jdbc";
    private static final Injector injector = Injector.getInstance(MAIN_PACKAGE_NAME);

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        manufacturerDao.create(audi);

        Manufacturer bmw = new Manufacturer();
        bmw.setName("bmw");
        bmw.setCountry("Germany");
        manufacturerDao.create(bmw);

        Manufacturer tesla = new Manufacturer();
        tesla.setName("Tesla");
        tesla.setCountry("USA");
        manufacturerDao.create(tesla);

        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");
        manufacturerDao.create(ford);

        Manufacturer renault = new Manufacturer();
        renault.setName("Renault");
        renault.setCountry("France");
        manufacturerDao.create(renault);
        System.out.println("*************** Added: audi, bmw, tesla, ford, renault");

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        System.out.println("*************** all Manufacturers from db was printed");

        audi.setName("Porsche");
        audi = manufacturerDao.update(audi);
        System.out.println("Audi was changed to Porsche");

        System.out.println(manufacturerDao.get(audi.getId()));
        System.out.println("*************** Manufacturer with id = "
                + audi.getId() + " was printed");

        manufacturerDao.delete(renault.getId());
        System.out.println("Renault was deleted");

        manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        System.out.println("*************** all Manufacturers from db was printed");
    }
}
