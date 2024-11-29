package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.BLL.NhanVienBLL;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.NhanVien;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

public class NhanVienController {
  @FXML
  private TextField username;
  @FXML
  private PasswordField password;
  @FXML
  private Button btnlogin, btnreset;
  @FXML
  private TextField fullname;
  @FXML
  private TextField email;
  @FXML
  private TextField phone;
  @FXML
  private Button btnRegister;
  @FXML
  private Text errorMessage;  // Thêm Text để hiển thị lỗi (nếu có)

  private final NhanVienBLL nhanVienBLL;

  public NhanVienController() {
    nhanVienBLL = new NhanVienBLL();
  }

  public void initialize() {
    // Thiết lập ban đầu nếu cần
  }

  @FXML
  private void handleDangNhap() {
    String username = this.username.getText();
    String password = this.password.getText();

    try {
      boolean isLoggedIn = nhanVienBLL.checkLogin(username, password);
      if (isLoggedIn) {
        System.out.println("Đăng nhập thành công!");
        // Có thể chuyển cảnh hoặc làm gì đó khác sau khi đăng nhập thành công
      } else {
        errorMessage.setText("Tên đăng nhập hoặc mật khẩu không đúng!");
        errorMessage.setVisible(true);
      }
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
      errorMessage.setVisible(true);
    }
  }

  @FXML
  private void handleReset() {
    username.clear();
    password.clear();
    errorMessage.setVisible(false);  // Ẩn thông báo lỗi khi người dùng nhấn nút Tạo lại
  }

  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void handleRegister() {
    String usernameText = username.getText();
    String passwordText = password.getText();
    String fullNameText = fullname.getText();
    String emailText = email.getText();
    String phoneText = phone.getText();

    // Kiểm tra dữ liệu đầu vào (có thể tùy chỉnh thêm)
    if (usernameText.isEmpty() || passwordText.isEmpty() || fullNameText.isEmpty() || emailText.isEmpty() || phoneText.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng điền đầy đủ thông tin!");
      return;
    }

    // Giả lập lưu thông tin (có thể thêm vào database)
    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng ký thành công!");

    // Reset form sau khi đăng ký
    username.clear();
    password.clear();
    fullname.clear();
    email.clear();
    phone.clear();
  }

}
