package daihocnhatrang.duongthianhhong.blueycoffee;

public class Account {
  private String name;  // Tên đăng nhập
  private String pass;  // Mật khẩu

  // Constructor
  public Account(String name, String pass) {
    this.name = name;
    this.pass = pass;
  }

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
