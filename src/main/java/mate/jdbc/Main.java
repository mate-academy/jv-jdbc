package mate.jdbc;

import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = ConnectionUtil.getConnection();) {
        } catch (SQLException e) {
            throw new DataProcessingException("", e);
        }
    }
}
