package mate.jdbc;

import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Germany");
        manufacturer.setName("Mercedes-Benz");
        ManufacturerDaoImpl manufacture = new ManufacturerDaoImpl();
        System.out.println(manufacture.create(manufacturer));
        System.out.println(manufacture.get(1L));
        System.out.println(manufacture.delete(2L));
        System.out.println(manufacture.update(manufacturer));
        System.out.println(manufacture.getAll());
    }
}
