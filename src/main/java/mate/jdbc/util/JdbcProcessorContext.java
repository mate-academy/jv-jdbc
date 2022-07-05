package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import mate.jdbc.exception.DataProcessingException;

public class JdbcProcessorContext {
    public Object processJdbc(String sql, Object[] sqlBindVariables, JdbcProcessor processor) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/taxi_service_db",
                "root", "12345678");
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
