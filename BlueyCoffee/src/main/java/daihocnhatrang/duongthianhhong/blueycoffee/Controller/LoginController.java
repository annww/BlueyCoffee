package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.ComonUtils;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;

import java.io.IOException;
import java.sql.*;

  public class LoginController {
    @FXML
    TextField username;
    @FXML
    PasswordField pass;
    @FXML
    Button btnlogin;
    @FXML
    Button btnSignUp;
    String account, password;
    Alert alert;
    void initial(){
      account = username.getText().toString();
      password = pass.getText().toString();
      System.out.println(ComonUtils.hashPassword(password));
    }

    public void Login() throws SQLException {
      initial();
      if (account.isEmpty() || password.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Lỗi đăng nhập", "Tên tài khoản và mật khẩu không để trống!");
        return;
      }
      Connection conn = DBUtils.openConnection();
      String sqlSelect = "SELECT * FROM nhanvien";
      Statement lenh = conn.createStatement();
      ResultSet ketQua = lenh.executeQuery(sqlSelect);
      boolean check = false;

      while (ketQua.next()) {
        if (account.equals(ketQua.getString("username")) &&
            ComonUtils.hashPassword(password).equals(ketQua.getString("password"))) {

          Current_data.username = ketQua.getString("tenNV");
          Current_data.userid = ketQua.getString("maNV");
          String maChucVu = ketQua.getString("chucVu");

          // Truy vấn loại chức vụ từ bảng chucvu
          String sqlSelectCV = "SELECT loaiChucVu FROM chucvu WHERE maCV = ?";
          PreparedStatement prepare = conn.prepareStatement(sqlSelectCV);
          prepare.setString(1, maChucVu);
          ResultSet chucVuKetQua = prepare.executeQuery();

          if (chucVuKetQua.next()) {
            Current_data.chucVu = chucVuKetQua.getString("loaiChucVu");
          }

          showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng nhập thành công!");
          switchToHomeScreen();
          check = true;
          break;
        }
      }

      if (!check) {
        showAlert(Alert.AlertType.ERROR, "Lỗi đăng nhập", "Tên đăng nhập hoặc mật khẩu không đúng!");
        System.out.println("Đăng nhập không thành công");
      }

      DBUtils.closeConnection(conn);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message){
      alert = new Alert(alertType);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
    }

    private void switchToHomeScreen() {
      try {
        StackPane stackPane = new StackPane();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/homeScreen.fxml"));
        fxmlLoader.setRoot(stackPane);
        Scene scene = new Scene(fxmlLoader.load());

        // Lấy Stage hiện tại
        Stage stage = (Stage) username.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Screen");
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    public void handleSignUp(ActionEvent event) {
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/signup.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // Lấy Stage hiện tại
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
