package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {

    public static void main(String[] args) {
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao factory = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        factory.create(bmw);
        factory.create(ford);
        System.out.println(factory.get(bmw.getId()));
        System.out.println(factory.get(ford.getId()));
        bmw.setCountry("Ukraine");
        bmw.setName("Mercedes");
        factory.update(bmw);
        System.out.println(factory.get(bmw.getId()));
        factory.delete(bmw.getId());
        factory.getAll().forEach(System.out::println);
        factory.getAll().forEach(m -> factory.delete(m.getId()));
    }
}
