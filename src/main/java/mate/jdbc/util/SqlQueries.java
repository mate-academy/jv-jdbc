package mate.jdbc.util;

public class SqlQueries {
    public static final String INSERT_MANUFACTURER =
            "INSERT INTO manufacturers(name, country) values(?, ?);";
    public static final String SELECT_ALL_MANUFACTURERS =
            "SELECT * FROM manufacturers;";
    public static final String UPDATE_MANUFACTURER =
            "UPDATE manufacturers SET name = ?, country = ?;";
    public static final String DELETE_MANUFACTURER_BY_ID =
            "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
}
