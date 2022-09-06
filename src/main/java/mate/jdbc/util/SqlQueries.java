package mate.jdbc.util;

public class SqlQueries {
    public static final String INSERT_MANUFACTURER =
            "INSERT INTO manufacturers(name, country) values(?, ?);";
    public static final String SELECT_ALL_MANUFACTURERS =
            "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
    public static final String UPDATE_MANUFACTURER =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? AND is_deleted = FALSE;";
    public static final String DELETE_MANUFACTURER_BY_ID =
            "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
    public static final String SELECT_MANUFACTURER_BY_ID =
            "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";

}
