package mate.jdbc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.service.CreateDataBase;
import mate.jdbc.util.DataSource;

public class CreateDataBaseImpl implements CreateDataBase {
    private DataSource dataSource;

    public CreateDataBaseImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createdTable(String stringSql) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(stringSql);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("No connection to base", e);
        }
    }
}
