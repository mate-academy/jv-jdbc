package mate.jdbc.model;

import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LiteraryFormatDaoImpl implements LiteraryFormatDao{
    @Override
    public List<LiteraryFormat> getAll() {
        List<LiteraryFormat> allFormats = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllDateStatement = connection.createStatement()) {
            ResultSet resultSet = getAllDateStatement
                    .executeQuery("SELECT * FROM literary_formats where is_deleted = false");
            while (resultSet.next()) {
                String format = resultSet.getString("format");
                Long id = resultSet.getLong("id");
                LiteraryFormat literaryFormat = new LiteraryFormat();
                literaryFormat.setId(id);
                literaryFormat.setFormat(format);
                allFormats.add(literaryFormat);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get all date from db", e);
        }
        return allFormats;
    }

    @Override
    public LiteraryFormat create(LiteraryFormat format) {
        String insertFormatReqest = "INSERT INTO literary_formats(format) values(?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createFormatStatement = connection.prepareStatement(insertFormatReqest, Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, format.getFormat());
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                format.setId(id);
            }
        } catch (SQLException e) {
                throw new RuntimeException("Cant create and insert format to  db", e);
        }
        return format;
    }

    @Override
    public LiteraryFormat get(Long id) {
        return null;
    }

    @Override
    public LiteraryFormat update(LiteraryFormat literaryFormat) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteReqest = "UPDATE literary_formats SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createFormatStatement =
                     connection.prepareStatement(deleteReqest, Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, String.valueOf(id));
            return createFormatStatement.executeUpdate() > 1;
        } catch (SQLException e) {
            throw new RuntimeException("Cant create and insert format to  db", e);
        }
    }
}
