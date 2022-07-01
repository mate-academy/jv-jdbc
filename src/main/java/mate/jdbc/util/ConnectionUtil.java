package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class ConnectionUtil {
    private static final String login;
    private static final String password;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
        try (Scanner scanLogin = new Scanner(System.in);
                Scanner scanPassword = new Scanner(System.in)) {
            System.out.println("Please type login:");
            login = scanLogin.nextLine();

            System.out.println("Please type password:");
            password = scanPassword.nextLine();
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", login);
            dbProperties.put("password", password);
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/db_taxi_service",
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection to DB", e);
        }
    }
}
