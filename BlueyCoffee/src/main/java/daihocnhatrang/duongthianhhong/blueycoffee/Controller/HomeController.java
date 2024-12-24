package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data.chucVu;
import static daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data.username;

public class HomeController implements Initializable {
  @FXML
  private AnchorPane homeScreen;
  @FXML
  Button btnBanHang;
  @FXML
  Button btnNhanVien;
  @FXML
  Button btnHoaDon;
  @FXML
  Button btnThongKe;
  @FXML
  Button btnSanPham;
  @FXML
  Hyperlink logOut;
  @FXML
  Label replaceName;

  private String username, chucVu;

  List<Button> btns;
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displayName();
  }

  @FXML
  private Alert alert;

  @FXML
  public void logout(){
    alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Đăng xuất");
    alert.setHeaderText(null);
    alert.setContentText("Bạn có chắc muốn đăng xuất?");
    Optional<ButtonType> option = alert.showAndWait();

    if(option.get().equals(ButtonType.OK)){
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/login.fxml"));
      Scene scene = null;
      logOut.getScene().getWindow().hide();
      try {
        scene = new Scene(fxmlLoader.load());
        // Lấy Stage hiện tại
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
  private void displayName(){
    username = Current_data.username;
    chucVu = Current_data.chucVu;
    String user = username + " - " + chucVu;
    replaceName.setText(user);
  }
}
