package mate.jdbc;

import mate.jdbc.util.ConnectionToDbUtil;
import mate.jdbc.util.DbPropertiesFileReader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties dbproperties = DbPropertiesFileReader
                .getPropertiesFrom("src/main/resources/DBProperties");
        System.out.println(dbproperties);
        Connection connection = ConnectionToDbUtil.getConnection(dbproperties);
        try {
            System.out.println(connection.getSchema());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
