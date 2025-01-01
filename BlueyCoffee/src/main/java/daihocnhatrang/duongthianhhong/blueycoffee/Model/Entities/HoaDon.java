package daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities;

import java.sql.Timestamp;

public class HoaDon {
  private String maHD, nguoiTao, ghiChu, thanhToan;
  private int tongTien;
  private Timestamp createdAt, confirmAt;

  public HoaDon(String maHD, String nguoiTao, Timestamp createdAt, Timestamp confirmAt, int tongTien, String thanhToan, String ghiChu) {
    this.maHD = maHD;
    this.nguoiTao = nguoiTao;
    this.createdAt = createdAt;
    this.confirmAt = confirmAt;
    this.tongTien = tongTien;
    this.thanhToan = thanhToan;
    this.ghiChu = ghiChu;
  }

  public String getMaHD() {
    return maHD;
  }

  public void setMaHD(String maHD) {
    this.maHD = maHD;
  }

  public String getNguoiTao() {
    return nguoiTao;
  }

  public void setNguoiTao(String nguoiTao) {
    this.nguoiTao = nguoiTao;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getConfirmAt() {
    return confirmAt;
  }

  public void setConfirmAt(Timestamp confirmAt) {
    this.confirmAt = confirmAt;
  }

  public int getTongTien() {
    return tongTien;
  }

  public void setTongTien(int tongTien) {
    this.tongTien = tongTien;
  }

  public String getThanhToan() {
    return thanhToan;
  }

  public void setThanhToan(String thanhToan) {
    this.thanhToan = thanhToan;
  }

  public String getGhiChu() {
    return ghiChu;
  }

  public void setGhiChu(String ghiChu) {
    this.ghiChu = ghiChu;
  }
}
