package mate.jdbc.dao;

import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ManufacturerDaoImplTest {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static ManufacturerDao manufacturerDao;
    private static Manufacturer manufacturer;
    private static final List<Manufacturer> manufacturerList = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturer = new Manufacturer();
        manufacturerList.add(new Manufacturer("BMW", "Germany"));
        manufacturerList.add(new Manufacturer("Ford", "USA"));
        manufacturerList.add(new Manufacturer("ZAZ", "Ukraine"));
        manufacturerList.add(new Manufacturer("Toyota", "Japan"));
        manufacturerList.add(new Manufacturer("KIA", "Korea"));
    }

    @Test
    void create_NewValidRow_Ok() {
        for (Manufacturer manufacturer : manufacturerList) {
            manufacturerDao.create(manufacturer);
        }
    }

    @Test
    void create_EmptyRow_NotOk() {
    }

    @Test
    void create_SameValidRow_Ok() {
    }

    @Test
    void get_existedId_Ok() {
    }

    @Test
    void get_notExistedId_NotOk() {
    }

    @Test
    void getAll_emptyDB_Ok() {
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer m : manufacturers) {
            System.out.println(m);
        }
    }

    @Test
    void getAll_normalDB_Ok() {
    }

    @Test
    void update_NewValidRow_Ok() {
        manufacturer.setName("TestManufacturer");
        manufacturer.setCountry("TestCountry");
        manufacturer.setId(3L);
        System.out.println(manufacturerDao.update(manufacturer));
    }

    @Test
    void update_SameValidRow_Ok() {
    }

    @Test
    void delete_existedRow_Ok() {
        manufacturerDao.delete(2L);
        manufacturerDao.delete(2L);
    }

    @Test
    void delete_notExistedRow_NotOk() {
    }
}