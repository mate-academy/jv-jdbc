package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer nissan = new Manufacturer();
        nissan.setName("Nissan");
        nissan.setCountry("Japan");
        manufacturerDao.create(nissan);

        Manufacturer porsche = new Manufacturer();
        porsche.setName("Porsche");
        porsche.setCountry("Germany");
        manufacturerDao.create(porsche);

        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");
        manufacturerDao.create(audi);

        Manufacturer hyundai = new Manufacturer();
        hyundai.setName("Hyundai");
        hyundai.setCountry("South_Korean");
        manufacturerDao.create(hyundai);

        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("United_States");
        manufacturerDao.create(ford);

        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("---------------------------- Create cars" + System.lineSeparator());

        nissan.setName("Ford");
        nissan = manufacturerDao.update(nissan);
        System.out.println("Update car -> " + nissan);

        System.out.println("Get car -> " + manufacturerDao.get(hyundai.getId()));

        System.out.println("Delete car -> " + manufacturerDao.delete(audi.getId()));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
