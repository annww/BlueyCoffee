package daihocnhatrang.duongthianhhong.blueycoffee.Model;

public class NhanVien {
  private int id;
  private String name;  // Tên đăng nhập
  private String pass;  // Mật khẩu

  public NhanVien(int id, String name, String pass) {
    this.id = id;
    this.name = name;
    this.pass = pass;
  }

  public int getId() {return id;}

  public void setId(int id) {this.id = id;}

  // Getter cho name
  public String getName() {
    return name;
  }

  // Setter cho name
  public void setName(String name) {
    this.name = name;
  }

  // Getter cho pass
  public String getPass() {
    return pass;
  }

  // Setter cho pass
  public void setPass(String pass) {
    this.pass = pass;
  }

  @Override
  public String toString() {
    return "Account{name='" + name + "', pass='" + pass + "'}";
  }
}
