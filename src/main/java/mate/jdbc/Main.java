package mate.jdbc;

import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();

    }
}
