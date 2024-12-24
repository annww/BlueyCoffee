package daihocnhatrang.duongthianhhong.blueycoffee.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DSUtils {
  public static Connection openConnection() {
    Connection con = null;
    try {
      // Đăng ký driver
      Class.forName("com.mysql.cj.jdbc.Driver");
      // Chuẩn bị chuỗi kết nối
      String connectionURL = "jdbc:mysql://localhost:3306/blueycoffee";
      // Mở kết nối
      con = DriverManager.getConnection(connectionURL, "root", "");
      System.out.println("Connection successful!");
    } catch (ClassNotFoundException e) {
      System.out.println("MySQL Driver not found!");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("Connection failed!");
      e.printStackTrace();
    }
    return con;
  }
  public static void closeConnection(Connection con) {
    if (con != null) {
      try {
        con.close();
        System.out.println("Connection closed!");
      } catch (SQLException e) {
        System.out.println("Failed to close connection!");
        e.printStackTrace();
      }
    }
  }
  }
