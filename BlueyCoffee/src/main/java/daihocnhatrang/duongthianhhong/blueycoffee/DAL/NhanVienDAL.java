package daihocnhatrang.duongthianhhong.blueycoffee.DAL;


import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.NhanVien;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.ComonUtils;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static daihocnhatrang.duongthianhhong.blueycoffee.Utils.AlertUtils.setAlert;

public class NhanVienDAL {
  Connection conn;
  public void addNV(NhanVien nv) {
    conn = DBUtils.openConnection();
    String sqlInsert = "INSERT INTO `nhanvien` (`maNV`, `tenNV`, `gioiTinh`, `ngaySinh`, `chucVu`, `SDT`, `email`, `anhNV`, `isWorking`, `username`, `password`) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    try {
      PreparedStatement lenh = conn.prepareStatement(sqlInsert);
      lenh.setString(1, nv.getMaNV());
      lenh.setString(2, nv.getTenNV());
      lenh.setBoolean(3, nv.isGioiTinh());
      lenh.setDate(4, nv.getNgaySinh());
      lenh.setString(5, nv.getChucVu());
      lenh.setString(6, nv.getSDT());
      lenh.setString(7, nv.getEmail());
      lenh.setString(8, nv.getAnhNV());
      lenh.setBoolean(9, nv.getIsWorking().equals("Đang làm"));
      lenh.setString(10, nv.getEmail());
      lenh.setString(11, ComonUtils.hashPassword(nv.getSDT()));
      int rowsInserted = lenh.executeUpdate();
      if (rowsInserted > 0) {
        setAlert(Alert.AlertType.INFORMATION, "Thêm", "Thêm nhân viên thành công");
      } else {
        setAlert(Alert.AlertType.INFORMATION, "Thêm", "Thêm nhân viên không thành công");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    DBUtils.closeConnection(conn);
  }

  public Connection getConn() {
    return conn;
  }

  public boolean Delete(int id){
    return true;
  }

  public boolean Login(String username, String hashedPassword) throws Exception {
    String sql = "SELECT COUNT(*) FROM nhanvien WHERE username = ? AND password = ?";
    try (Connection conn = DBUtils.openConnection();
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
