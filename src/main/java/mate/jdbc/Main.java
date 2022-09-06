package mate.jdbc;

import exception.DataProcessingException;
import util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        Statement getAllManufacturerStatement = null;
        try {
            getAllManufacturerStatement = connection.createStatement();
            ResultSet resultSet = getAllManufacturerStatement.executeQuery("SELECT * FROM manufacturers");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all formats from DB", e);
        }
    }
}
