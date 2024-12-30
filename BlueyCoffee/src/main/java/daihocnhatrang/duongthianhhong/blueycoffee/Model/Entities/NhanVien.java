package daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities;

import java.sql.Date;
import java.sql.Timestamp;

public class NhanVien {
  String maNV, tenNV, chucVu, SDT, email, username, password, anhNV, isWorking;
  Timestamp createdAt, updatedAt;
  boolean gioiTinh;
  Date ngaySinh;

  public NhanVien(String maNV, String tenNV, String chucVu, String sdt, String email, String username, String password, String anhNV, String isWorking, Timestamp createdAt, Timestamp updatedAt, boolean gioiTinh, Date ngaySinh) {
  }

  public NhanVien(String maNV, String tenNV, String chucVu, String SDT, String email, String password, String anhNV, String isWorking, Timestamp createAt, Timestamp updatedAt, boolean gioiTinh, Date ngaySinh) {
    this.maNV = maNV;
    this.tenNV = tenNV;
    this.chucVu = chucVu;
    this.SDT = SDT;
    this.email = email;
    this.password = password;
    this.anhNV = anhNV;
    this.isWorking = isWorking;
    this.createdAt = createAt;
    this.updatedAt = updatedAt;
    this.gioiTinh = gioiTinh;
    this.ngaySinh = ngaySinh;
  }

  public NhanVien(String maNV, String tenNV, boolean gioiTinh, Date ngaySinh, String chucVu, String sdt, String email, String anhNV, String isWorking, String username, String password, Timestamp createdAt, Object ngaySinh1) {
    this.maNV= maNV;
    this.tenNV = tenNV;
    this.gioiTinh = gioiTinh;
    this.ngaySinh = ngaySinh;
    this.chucVu = chucVu;
    this.SDT = sdt;
    this.email = email;
    this.anhNV = anhNV;
    this.isWorking = isWorking;
    this.username = username;
    this.password = password;
    this.createdAt = createdAt;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMaNV() {
    return maNV;
  }

  public void setMaNV(String maNV) {
    this.maNV = maNV;
  }

  public String getTenNV() {
    return tenNV;
  }

  public void setTenNV(String tenNV) {
    this.tenNV = tenNV;
  }

  public String getChucVu() {
    return chucVu;
  }

  public void setChucVu(String chucVu) {
    this.chucVu = chucVu;
  }

  public String getSDT() {
    return SDT;
  }

  public void setSDT(String SDT) {
    this.SDT = SDT;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAnhNV() {
    return anhNV;
  }

  public void setAnhNV(String anhNV) {
    this.anhNV = anhNV;
  }

  public String getIsWorking() {
    return isWorking;
  }

  public void setIsWorking(String isWorking) {
    this.isWorking = isWorking;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createAt) {
    this.createdAt = createAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isGioiTinh() {
    return gioiTinh;
  }

  public String getGioiTinhText() {
    return gioiTinh ? "Nam" : "Nữ";
  }


  public void setGioiTinh(boolean gioiTinh) {
    this.gioiTinh = gioiTinh;
  }

  public Date getNgaySinh() {
    return ngaySinh;
  }

  public void setNgaySinh(Date ngaySinh) {
    this.ngaySinh = ngaySinh;
  }
}
