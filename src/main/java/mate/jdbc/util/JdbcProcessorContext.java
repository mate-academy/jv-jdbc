package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import mate.jdbc.exception.DataProcessingException;

public class JdbcProcessorContext {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public Object processJdbc(String sql, Object[] sqlBindVariables, JdbcProcessor processor) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                PreparedStatement statement =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < sqlBindVariables.length; i++) {
                statement.setObject(i + 1, sqlBindVariables[i]);
            }
            return processor.process(statement);
        } catch (SQLException e) {
            throw new DataProcessingException("Database access error", e);
        }
    }
}
