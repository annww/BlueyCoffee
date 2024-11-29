package daihocnhatrang.duongthianhhong.blueycoffee.DAL;


import daihocnhatrang.duongthianhhong.blueycoffee.Model.NhanVien;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DSUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVienDAL {
  public void addNhanVien(NhanVien nhanVien) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO NhanVien (id, username, password, fullname, email, phone) VALUES (?, ?, ?, ?, ?, ?)";

    // Sử dụng try-with-resources để tự động đóng tài nguyên
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      // Thiết lập các tham số vào câu lệnh PreparedStatement
      stmt.setInt(1, nhanVien.getId());
      stmt.setString(2, nhanVien.getUsername());
      stmt.setString(3, nhanVien.getPassword());
      stmt.setString(4, nhanVien.getFullname());
      stmt.setString(5, nhanVien.getEmail());
      stmt.setString(6, nhanVien.getPhone());

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
    String sql = "SELECT COUNT(*) FROM NhanVien WHERE username = ? AND password = ?";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, username);
      stmt.setString(2, hashedPassword);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1) > 0; // Trả về true nếu có ít nhất một bản ghi
        }
      }
    } catch (Exception e) {
      throw new Exception("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
    return false;
  }
}
