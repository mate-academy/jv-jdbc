package mate.jdbc.dao.mappers;

import java.sql.ResultSet;
import java.util.function.Function;

@FunctionalInterface
public interface Mapper<T> extends Function<ResultSet, T> {
}
