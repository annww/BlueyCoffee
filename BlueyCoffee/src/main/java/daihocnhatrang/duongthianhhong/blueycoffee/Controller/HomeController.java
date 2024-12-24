package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
  @FXML
  AnchorPane banHangform;
  @FXML
  AnchorPane nhanVienform;
  @FXML
  AnchorPane hoaDonform;
  @FXML
  AnchorPane thongKeForm;
  @FXML
  AnchorPane sanPhamform;
  private String username, chucVu;

  List<Button> btns;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displayName();
    btns = new ArrayList<>(Arrays.asList(btnBanHang, btnHoaDon, btnThongKe, btnNhanVien, btnSanPham));
  }

  @FXML
  private Alert alert;

  @FXML
  public void logout() {
    alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Đăng xuất");
    alert.setHeaderText(null);
    alert.setContentText("Bạn có chắc muốn đăng xuất?");
    Optional<ButtonType> option = alert.showAndWait();

    if (option.get().equals(ButtonType.OK)) {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/login.fxml"));
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

  private void displayName() {
    username = Current_data.username;
    chucVu = Current_data.chucVu;
    String user = username + " - " + chucVu;
    replaceName.setText(user);
  }

  public void addChildScene(AnchorPane form, Parent childRoot) {
    form.getChildren().clear(); // xóa tất cả các con hiện tại
    form.getChildren().add(childRoot);
    AnchorPane.setTopAnchor(childRoot, 0.0);
    AnchorPane.setBottomAnchor(childRoot, 0.0);
    AnchorPane.setLeftAnchor(childRoot, 0.0);
    AnchorPane.setRightAnchor(childRoot, 0.0);
  }

  private void resetColorButton() {
    for (Button btn : btns) {
      boolean hasStyle = btn.getStyleClass().contains("nav-button-choose");
      if (hasStyle) btn.getStyleClass().remove("nav-button-choose");
    }
  }

    private void setViewInvisible(){
      banHangform.setVisible(false);
      nhanVienform.setVisible(false);
      hoaDonform.setVisible(false);
      thongKeForm.setVisible(false);
      sanPhamform.setVisible(false);
    }

    @FXML
    private void setBtnBanHang () {
      try {
        resetColorButton();
        btnBanHang.getStyleClass().add("nav-button-choose");
        setViewInvisible();
        banHangform.setVisible(true);
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/banhang.fxml"));
        Parent productRoot = productLoader.load();
        addChildScene(banHangform, productRoot);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  @FXML
  private void setBtnNhanVien () {
    try {
      resetColorButton();
      btnBanHang.getStyleClass().add("nav-button-choose");
      setViewInvisible();
      banHangform.setVisible(true);
      FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/nhanvien.fxml"));
      Parent productRoot = productLoader.load();
      addChildScene(banHangform, productRoot);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void setBtnHoaDon () {
    try {
      resetColorButton();
      btnBanHang.getStyleClass().add("nav-button-choose");
      setViewInvisible();
      banHangform.setVisible(true);
      FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/hoadon.fxml"));
      Parent productRoot = productLoader.load();
      addChildScene(banHangform, productRoot);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void setBtnThongKe () {
    try {
      resetColorButton();
      btnBanHang.getStyleClass().add("nav-button-choose");
      setViewInvisible();
      banHangform.setVisible(true);
      FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/thongke.fxml"));
      Parent productRoot = productLoader.load();
      addChildScene(banHangform, productRoot);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
    @FXML
    private void setBtnSanPham () {
      try {
        resetColorButton();
        btnBanHang.getStyleClass().add("nav-button-choose");
        setViewInvisible();
        banHangform.setVisible(true);
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/sanpham.fxml"));
        Parent productRoot = productLoader.load();
        addChildScene(banHangform, productRoot);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
