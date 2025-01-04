package daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities;

public class CTHD {
  private String maHD, maSP, tenSP, ghiChu;
  private int donGia, soLuong, thanhTien;

  // Constructor
  public CTHD(String maHD, String maSP, String tenSP, int donGia, int soLuong, String ghiChu) {
    this.maHD = maHD;
    this.maSP = maSP;
    this.tenSP = tenSP;
    this.donGia = donGia;
    this.soLuong = soLuong;
    this.ghiChu = ghiChu;
    updateThanhTien();  // Tính thanh tiền khi khởi tạo
  }

  public CTHD(String maSP, String tenSP, int donGia, int soLuong) {
    this.maSP = maSP;
    this.tenSP = tenSP;
    this.donGia = donGia;
    this.soLuong = soLuong;
    this.ghiChu = "";  // Khởi tạo ghi chú mặc định là chuỗi rỗng
    updateThanhTien();
  }

  public CTHD(String maSP, String tenSP, String tenSP1, int donGia, int soLuong) {
    this.maSP = maSP;
    this.tenSP = tenSP;
    this.donGia = donGia;
    this.soLuong = soLuong;
  }

  // Getter và Setter cho các thuộc tính
  public String getMaHD() {
    return maHD;
  }

  public void setMaHD(String maHD) {
    this.maHD = maHD;
  }

  public String getMaSP() {
    return maSP;
  }

  public String getTenSP() {
    return tenSP;
  }

  public void setTenSP(String tenSP) {
    this.tenSP = tenSP;
  }

  public String getGhiChu() {
    return ghiChu;
  }

  public void setGhiChu(String ghiChu) {
    this.ghiChu = ghiChu;
  }

  public int getDonGia() {
    return donGia;
  }

  public void setDonGia(int donGia) {
    this.donGia = donGia;
    updateThanhTien();  // Cập nhật lại thành tiền nếu thay đổi đơn giá
  }

  public int getSoLuong() {
    return soLuong;
  }

  public void setSoLuong(int soLuong) {
    this.soLuong = soLuong;
    updateThanhTien();  // Cập nhật lại thành tiền nếu thay đổi số lượng
  }

  public int getThanhTien() {
    return thanhTien;
  }

  // Phương thức này dùng để cập nhật thanh tiền mỗi khi số lượng hoặc đơn giá thay đổi
  private void updateThanhTien() {
    this.thanhTien = this.soLuong * this.donGia;
  }

  public void setThanhTien(int thanhTien) {
    this.thanhTien = thanhTien;
  }
}
