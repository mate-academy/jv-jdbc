package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger loggerMain = LogManager.getLogger(Main.class);
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        //set to columns
        manufacturer.setName("Tavria");
        manufacturer.setCountry("Ukraine");
        Manufacturer manufacturerFromDb = manufacturerDao.create(manufacturer);
        loggerMain.info(manufacturer);
        loggerMain.info("--------------------");

        //get from table
        loggerMain.info("Get manufacturer");
        Optional<Manufacturer> optional = manufacturerDao.get(manufacturerFromDb.getId());
        loggerMain.info(optional);
        loggerMain.info("--------------------");

        //get all from table
        loggerMain.info("GetAll manufacturer");
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        for (Manufacturer item : allManufacturer) {
            loggerMain.info(item);
        }
        loggerMain.info("--------------------");

        //update table
        loggerMain.info("Update manufacturer");
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(manufacturer.getId());
        updateManufacturer.setName("Toyota");
        updateManufacturer.setCountry("Japan");
        loggerMain.info(manufacturerDao.update(updateManufacturer));
        loggerMain.info("--------------------");

        //delete from table
        loggerMain.info("Delete manufacturer");
        loggerMain.info(manufacturerDao.delete(manufacturer.getId()));
        loggerMain.info("--------------------");
    }
}
