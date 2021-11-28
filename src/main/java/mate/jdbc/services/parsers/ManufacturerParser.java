package mate.jdbc.services.parsers;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.models.DBModel;
import mate.jdbc.models.db.models.Manufacturer;

public class ManufacturerParser implements DBModelParser {
    @Override
    public List<Manufacturer> parse(ResultSet resultSet) {
        List<Manufacturer> resultList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                resultList.add(parseRow(resultSet));
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get asses to ResultSet.", e);
        }
    }

    public Manufacturer parseRow(ResultSet resultSet) {
        try {
            Manufacturer model = new Manufacturer();
            for (Field field : Manufacturer.class.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(model, resultSet.getObject(field.getName(), field.getType()));
            }
            return model;
        } catch (SQLException e) {
            throw new RuntimeException("Can't convert resultSet to model.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cant get asses to the field.", e);
        }
    }
}
