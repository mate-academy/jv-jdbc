package mate.jdbc.dao;

import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class ManufacturerDaoImplTest {

    private static final String TEST_NAME = "LAMBORGINI";
    private static final String TEST_COUNTRY = "ITALY";
    private static final ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
    private static Manufacturer manufacturer;

    //TODO: 1.Use Injector
    @BeforeClass
    public static void setUp() {
        manufacturer = new Manufacturer();
        manufacturer.setCountry(TEST_COUNTRY);
        manufacturer.setName(TEST_NAME);
    }

    @Test
    public void createManufacturer_Ok() {

        String insertQuery = "INSERT INTO manufacturers(name, country) values('"
                + TEST_NAME + "', '" + TEST_COUNTRY + "');";
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement createStatement =
                         connection.createStatement()) {
                connection.setAutoCommit(false);
                // Initial cleanup:
                createStatement.executeUpdate("DELETE FROM manufacturers;");
                // Javabean Checks: Check the javabean contains the expected values:
                assertEquals(TEST_NAME, manufacturer.getName());
                assertEquals(TEST_COUNTRY, manufacturer.getCountry());
                createStatement.executeUpdate(insertQuery);
                // Database Checks:
                // Check the Manufacturers table contains one row with the expected values:
//                Long idManufacturer;
                try (ResultSet rs = createStatement.executeQuery("SELECT * FROM manufacturers;")) {
                    Assert.assertTrue(rs.next());
//                    idManufacturer = rs.getLong("id");
                    assertEquals(TEST_COUNTRY, rs.getString("country"));
                    assertEquals(TEST_NAME, rs.getString("name"));
                    assertFalse(rs.next());
                }
            } finally {
                // Undo the testing operations:
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect to database ", e);
        }
    }

    @Test //(expected = DataProcessingException.class)
    public void create_DataProcExc_NotOk() {
        String insertQuery = "ALTER TABLE `taxi_service_db`.`manufacturers` \n" +
                "CHANGE COLUMN `name` `name_1` VARCHAR(255) NULL DEFAULT NULL ;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement deleteStatement =
                         connection.createStatement()) {
                deleteStatement.executeUpdate("DELETE FROM manufacturers;");
                //deleteStatement.executeUpdate(insertQuery);
                //manufacturerDao.create(manufacturer);
                assertThrows(DataProcessingException.class, ()
                         -> manufacturerDao.create(manufacturer));

            } finally {
                // Undo the testing operations:
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect to database ", e);
        }
    }
}

