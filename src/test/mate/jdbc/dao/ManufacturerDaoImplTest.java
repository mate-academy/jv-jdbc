package mate.jdbc.dao;

import static org.junit.Assert.*;

import mate.jdbc.models.Manufacturer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;
import java.util.Optional;

public class ManufacturerDaoImplTest {
    private static ManufacturerDao manufacturerDao;

    @BeforeClass
    public static void setUp() throws Exception {
        manufacturerDao = new ManufacturerDaoImpl();
    }

    @Test
    public void create_OK() {
        Manufacturer actual = Manufacturer.builder()
                .name("Toshiba")
                .country("Japan")
                .build();
        actual =  manufacturerDao.create(actual);
        assertNotNull(actual.getId());
    }

    @Test
    public void getById_Ok() {
        Manufacturer actual = Manufacturer.builder()
                .name("Hitachi")
                .country("China")
                .build();
        actual = manufacturerDao.create(actual);
        Optional<Manufacturer> expected = manufacturerDao.getById(actual.getId());
        assertEquals(expected.orElse(new Manufacturer()), actual);
    }

    @Test
    public void getAll_Ok() {
        List<Manufacturer> actual = manufacturerDao.getAll();
        assertFalse(actual.isEmpty());
    }

    @Test
    public void update_Ok() {
        Manufacturer manufacturer = Manufacturer.builder()
                .name("Yamaha")
                .country("Japan")
                .build();
        manufacturer =  manufacturerDao.create(manufacturer);
        manufacturer.setCountry("China");
        manufacturer = manufacturerDao.update(manufacturer);
        assertEquals("China", manufacturer.getCountry());
    }

    @Test
    public void delete_Ok() {
        Manufacturer manufacturer = Manufacturer.builder()
                .name("HyperX")
                .country("USA")
                .build();
        manufacturer = manufacturerDao.create(manufacturer);
        boolean isDeleted = manufacturerDao.delete(manufacturer.getId());
        assertTrue(isDeleted);
    }
}
