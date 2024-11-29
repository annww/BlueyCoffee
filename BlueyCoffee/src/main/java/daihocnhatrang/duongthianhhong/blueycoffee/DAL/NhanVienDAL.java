package daihocnhatrang.duongthianhhong.blueycoffee.DAL;


import daihocnhatrang.duongthianhhong.blueycoffee.Model.NhanVien;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DSUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVienDAL {
  public void addNhanVien(NhanVien nhanVien) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO NhanVien (id, username, password) VALUES (?, ?, ?)";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, nhanVien.getId());
      stmt.setString(2, nhanVien.getName());
      stmt.setString(3, nhanVien.getPass()); // Password đã được băm ở BLL

      stmt.executeUpdate();
      DSUtils.CloseConnect(conn);
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
