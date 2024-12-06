package daihocnhatrang.duongthianhhong.blueycoffee.DAL;


import daihocnhatrang.duongthianhhong.blueycoffee.Model.NhanVien;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DSUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVienDAL {
  public void addNhanVien(NhanVien nhanVien) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO nhanvien (fullname, email, phone, username, password) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, nhanVien.getFullname());
      stmt.setString(2, nhanVien.getEmail());
      stmt.setString(3, nhanVien.getPhone());
      stmt.setString(4, nhanVien.getUsername());
      stmt.setString(5, nhanVien.getPassword());
      stmt.executeUpdate();

    } catch (SQLException | ClassNotFoundException e) {

      System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());
      throw e;
    }
  }

  public boolean Delete(int id){
    return true;
  }

  public boolean Login(String username, String hashedPassword) throws Exception {
    String sql = "SELECT COUNT(*) FROM nhanvien WHERE username = ? AND password = ?";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, username);
      stmt.setString(2, hashedPassword);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1) > 0;
        }
      }
    } catch (Exception e) {
      throw new Exception("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
    return false;
  }
}
