package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.lib.ManufacturerDao;
import mate.jdbc.lib.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao dao = (ManufacturerDaoImpl) INJECTOR.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        Manufacturer toUpdate = new Manufacturer();
        toUpdate.setCountry("USA");
        toUpdate.setName("Ford");
        toUpdate.setId(1L);
        manufacturer.setName("BMW");
        manufacturer.setCountry("Germany");
        Manufacturer createdManufacturer = dao.create(manufacturer);
        System.out.println(createdManufacturer);
        Manufacturer updated = dao.update(toUpdate);
        Optional<Manufacturer> optional = dao.get(1L);
        System.out.println(optional);
        dao.delete(1L);
        List<Manufacturer> manufacturers = dao.getAll();

    }
}
