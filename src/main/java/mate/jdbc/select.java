package mate.jdbc;

import java.sql.*;

public class select {
//    String public static dburl="jdbc:mysql://localhost:3306/telega";
    public static String  dbusername="root";
    public static String  dbpassword="nsqry6c82i";
    public static void myselect(){
      String  dburl="jdbc:mysql://localhost:3306/jtelega";
      String  dbusername="root";
      String  dbpassword="nsqry6c82i";
        try {
   //         Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection(dburl, dbusername, dbpassword);
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()){
                System.out.print(resultSet.getString(1)+" ");
                System.out.print(resultSet.getString(2)+" ");
                System.out.print(resultSet.getString(3)+" ");
                System.out.print(resultSet.getString(4)+" ");
                System.out.print(resultSet.getString(5)+" ");
                System.out.print(resultSet.getString(6)+" ");
                System.out.print(resultSet.getString(7)+" ");
                System.out.print(resultSet.getString(8)+" ");
                System.out.print(resultSet.getString(9)+" ");
                System.out.print(resultSet.getString(10)+" ");
                System.out.print(resultSet.getString(11)+" ");
                System.out.println(" ");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
 //       } catch (ClassNotFoundException e) {
 //           e.printStackTrace();
        }
    }
    public static void myselect2(){
        String sqlSelectAllPersons = "SELECT * FROM users";
        String connectionUrl = "jdbc:mysql://localhost:3306/telega?serverTimezone=UTC";
        System.out.println(connectionUrl+ dbusername+ dbpassword);
        try (Connection conn = DriverManager.getConnection(connectionUrl, dbusername,dbpassword);
             PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("DATA:");
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("surname");
                System.out.println(id+" "+name+" "+lastName);
                // do something with the extracted data...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
