package daihocnhatrang.duongthianhhong.blueycoffee.BLL;

import daihocnhatrang.duongthianhhong.blueycoffee.DAL.NhanVienDAL;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.NhanVien;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.ComonUtils;
public class NhanVienBLL {
  NhanVienDAL nhanVienDAL;

  public NhanVienBLL() {
    this.nhanVienDAL = new NhanVienDAL(); // Khởi tạo DAL bên trong
  }

  public void addNhanVien(NhanVien nhanVien) throws Exception {
    // Kiểm tra tính hợp lệ của tên đăng nhập
    if (nhanVien.getUsername() == null || nhanVien.getUsername().isEmpty()) {
      throw new Exception("Tên nhân viên không được để trống.");
    }

    // Kiểm tra tính hợp lệ của mật khẩu
    if (nhanVien.getPassword() == null || nhanVien.getPassword().isEmpty()) {
      throw new Exception("Mật khẩu không được để trống.");
    }

    // Kiểm tra tính hợp lệ của họ tên
    if (nhanVien.getTenNV() == null || nhanVien.getTenNV().isEmpty()) {
      throw new Exception("Họ tên không được để trống.");
    }

    // Kiểm tra tính hợp lệ của email
    if (nhanVien.getEmail() == null || nhanVien.getEmail().isEmpty()) {
      throw new Exception("Email không được để trống.");
    } else if (!nhanVien.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
      throw new Exception("Email không hợp lệ.");
    }

    // Kiểm tra tính hợp lệ của số điện thoại
    if (nhanVien.getSDT() == null || nhanVien.getSDT().isEmpty()) {
      throw new Exception("Số điện thoại không được để trống.");
    } else if (!nhanVien.getSDT().matches("^\\d{10,15}$")) {
      throw new Exception("Số điện thoại không hợp lệ. Vui lòng nhập từ 10 đến 15 chữ số.");
    }

    // Băm mật khẩu trước khi gửi xuống DAL
    String hashedPassword = ComonUtils.hashPassword(nhanVien.getPassword());
    nhanVien.setPassword(hashedPassword);

    // Gửi xuống DAL để lưu vào CSDL
    nhanVienDAL.addNV(nhanVien);
  }


  public boolean checkLogin(String tenDN, String matKhau) throws Exception {
    // Kiểm tra dữ liệu đầu vào
    if (tenDN == null || tenDN.trim().isEmpty()) {
      throw new Exception("Tên đăng nhập không được để trống.");
    }
    if (matKhau == null || matKhau.trim().isEmpty()) {
      throw new Exception("Mật khẩu không được để trống.");
    }

    // Băm mật khẩu trước khi so sánh
    String hashedPassword = ComonUtils.hashPassword(matKhau);

    // Gọi DAL để kiểm tra
    return nhanVienDAL.Login(tenDN, hashedPassword);
  }
}