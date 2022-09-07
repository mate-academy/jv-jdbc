package mate.jdbc;

import mate.jdbc.lib.ManufacturerDao;
import mate.jdbc.lib.ManufacturerDaoImpl;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
    }
}
