package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");
    public static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");

        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");

        Manufacturer chery = new Manufacturer();
        chery.setName("Chery");
        chery.setCountry("China");

        Manufacturer hyundai = new Manufacturer();
        hyundai.setName("Hundai");
        hyundai.setCountry("Corea");

        Manufacturer vaz = new Manufacturer();
        vaz.setName("VAZ");
        vaz.setCountry("Russia");

        System.out.println("Creating audi manufacturer");
        manufacturerDao.create(audi);
        System.out.println("Creating bmw manufacturer");
        manufacturerDao.create(bmw);
        System.out.println("Creating Chery manufacturer");
        manufacturerDao.create(chery);
        System.out.println("Creating Hyundai manufacturer");
        manufacturerDao.create(hyundai);
        System.out.println("Creating VAZ manufacturer");
        manufacturerDao.create(vaz);

        System.out.println("Deleting Chery manufacturer");
        manufacturerDao.delete(chery.getId());

        System.out.println("Getting all manufacturers");
        manufacturerDao.getAll();

        System.out.println("Changing information Hyundai manufacturer");
        hyundai.setCountry("Korea");
        hyundai.setName("Hyundai");
        manufacturerDao.update(hyundai);

        System.out.println("Getting separate manufacturer");
        manufacturerDao.get(vaz.getId());

        System.out.println("Getting all manufacturers");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
