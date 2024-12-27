package daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities;

public class CTHD {
  String tenSP, ghiChu, maSP;
  int donGia, soLuong, thanhTien;

  public CTHD(String tenSP, String ghiChu, String maSP, int donGia, int soLuong, int thanhTien) {
    this.tenSP = tenSP;
    this.ghiChu = ghiChu;
    this.maSP = maSP;
    this.donGia = donGia;
    this.soLuong = soLuong;
    this.thanhTien = thanhTien;
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

  public String getMaSP() {
    return maSP;
  }

  public void setMaSP(String maSP) {
    this.maSP = maSP;
  }

  public int getDonGia() {
    return donGia;
  }

  public void setDonGia(int donGia) {
    this.donGia = donGia;
  }

  public int getSoLuong() {
    return soLuong;
  }

  public void setSoLuong(int soLuong) {
    this.soLuong = soLuong;
  }

  public int getThanhTien() {
    return thanhTien;
  }

  public void setThanhTien(int thanhTien) {
    this.thanhTien = thanhTien;
  }
}
