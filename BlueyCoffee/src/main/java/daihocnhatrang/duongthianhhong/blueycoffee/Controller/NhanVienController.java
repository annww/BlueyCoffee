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
  private void handleDangNhap(ActionEvent event) {
    // Lấy dữ liệu từ các trường nhập liệu
    String inputUsername = username.getText().trim();
    String inputPassword = password.getText().trim();

    // Kiểm tra nếu các trường nhập liệu trống
    if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
      errorMessage.setText("Vui lòng điền đầy đủ tên đăng nhập và mật khẩu!");
      errorMessage.setVisible(true);
      return;
    }

    try {
      // Kiểm tra đăng nhập thông qua lớp BLL
      boolean isLoggedIn = nhanVienBLL.checkLogin(inputUsername, inputPassword);

      if (isLoggedIn) {
        // Đăng nhập thành công, chuyển đến giao diện chính
        loadFXML("/resources/stranglehold/duongthianhhong/blueycoffee/trangchu.fxml", event);
        System.out.println("Đăng nhập thành công!");
      } else {
        // Hiển thị lỗi nếu đăng nhập thất bại
        errorMessage.setText("Tên đăng nhập hoặc mật khẩu không đúng!");
        errorMessage.setVisible(true);
      }
    } catch (Exception e) {
      // Hiển thị thông báo lỗi khi gặp ngoại lệ
      errorMessage.setText("Có lỗi xảy ra: " + e.getMessage());
      errorMessage.setVisible(true);
      e.printStackTrace();
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
