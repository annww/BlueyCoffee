package daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities;

public class NhanVien {
  private int id;
  private String username;  // Tên đăng nhập
  private String password;  // Mật khẩu
  private String fullname;  // Họ tên
  private String email;     // Email
  private String phone;     // Số điện thoại

  // Constructor đầy đủ
  public NhanVien(int id, String username, String password, String fullname, String email, String phone) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.fullname = fullname;
    this.email = email;
    this.phone = phone;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  // Getter và Setter cho password
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "NhanVien{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", fullname='" + fullname + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        '}';
  }
}
