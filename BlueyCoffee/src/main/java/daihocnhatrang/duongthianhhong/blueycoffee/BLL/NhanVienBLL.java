package daihocnhatrang.duongthianhhong.blueycoffee.BLL;

import daihocnhatrang.duongthianhhong.blueycoffee.DAL.NhanVienDAL;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.NhanVien;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.ComonUtils;
public class NhanVienBLL {
  NhanVienDAL nhanVienDAL;

  public NhanVienBLL() {
    this.nhanVienDAL = new NhanVienDAL(); // Khởi tạo DAL bên trong
  }

  public void addNhanVien(NhanVien nhanVien) throws Exception {
    // Kiểm tra tính hợp lệ
    if (nhanVien.getName() == null || nhanVien.getName().isEmpty()) {
      throw new Exception("Tên nhân viên không được để trống.");
    }
    if (nhanVien.getPass() == null || nhanVien.getPass().isEmpty()) {
      throw new Exception("Mật khẩu không được để trống.");
    }

    // Băm mật khẩu trước khi gửi xuống DAL
    String hashedPassword = ComonUtils.hashPassword(nhanVien.getPass());
    nhanVien.setPass(hashedPassword);

    // Gửi xuống DAL để lưu vào CSDL
    nhanVienDAL.addNhanVien(nhanVien);
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