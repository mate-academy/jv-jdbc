package mate.jdbc;

import dao.ManufacturerDao;
import dao.impl.ManufacturerDaoImpl;
import exception.DataProcessingException;
import model.Manufacturer;
import util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        List<Manufacturer> allManufacturer = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturer) {
            System.out.println(manufacturer);
        }
    }
}
