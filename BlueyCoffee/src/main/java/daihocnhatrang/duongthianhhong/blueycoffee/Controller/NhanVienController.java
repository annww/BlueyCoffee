package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.BLL.NhanVienBLL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

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
  }

  @FXML
  private void handleDangNhap(ActionEvent event) {
    String inputUsername = username.getText().trim();
    String inputPassword = password.getText().trim();

    if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
      errorMessage.setText("Vui lòng điền đầy đủ tên đăng nhập và mật khẩu!");
      errorMessage.setVisible(true);
      return;
    }

    try {
      // Kiểm tra đăng nhập thông qua lớp BLL
      boolean isLoggedIn = nhanVienBLL.checkLogin(inputUsername, inputPassword);

      if (isLoggedIn) {
        loadFXML("/resources/stranglehold/duongthianhhong/blueycoffee/trangchu.fxml", event);
        System.out.println("Đăng nhập thành công!");
      } else {
        errorMessage.setText("Tên đăng nhập hoặc mật khẩu không đúng!");
        errorMessage.setVisible(true);
      }
    } catch (Exception e) {
      errorMessage.setText("Có lỗi xảy ra: " + e.getMessage());
      errorMessage.setVisible(true);
      e.printStackTrace();
    }
  }

  @FXML
  private void handleReset() {
    username.clear();
    password.clear();
    errorMessage.setVisible(false);
  }

  private void loadFXML(String fxmlPath, ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
    Parent root = fxmlLoader.load();

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    stage.setScene(new Scene(root));
    stage.setResizable(true);
    stage.centerOnScreen();
    stage.show();
  }

  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void handleDangKy(ActionEvent event) {
    try {
      loadFXML("/resources/daihocnhatrang/duongthianhhong/blueycoffee/signup.fxml", event);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}
