package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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

  @FXML
  private ImageView imgUser;

  List<Button> btns;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displayName();
    btns = new ArrayList<>(Arrays.asList(btnBanHang, btnHoaDon, btnThongKe, btnNhanVien, btnSanPham));
    String user = Current_data.username; // Lấy mã nhân viên từ dữ liệu hiện tại
    if (user != null) {
      loadUserImage(user); // Hiển thị ảnh nhân viên
    } else {
      System.err.println("Mã nhân viên không tồn tại. Không thể tải ảnh.");
    }
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
      btnNhanVien.getStyleClass().add("nav-button-choose");
      setViewInvisible();
      nhanVienform.setVisible(true);
      FXMLLoader employeeLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/nhanvien.fxml"));
      Parent productRoot = employeeLoader.load();
      addChildScene(nhanVienform, productRoot);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void setBtnHoaDon () {
    try {
      resetColorButton();
      btnHoaDon.getStyleClass().add("nav-button-choose");
      setViewInvisible();
      hoaDonform.setVisible(true);
      FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/hoadon.fxml"));
      Parent productRoot = productLoader.load();
      addChildScene(hoaDonform, productRoot);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void setBtnThongKe () {
    try {
      resetColorButton();
      btnThongKe.getStyleClass().add("nav-button-choose");
      setViewInvisible();
      thongKeForm.setVisible(true);
      FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/thongke.fxml"));
      Parent productRoot = productLoader.load();
      addChildScene(thongKeForm, productRoot);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
    @FXML
    private void setBtnSanPham () {
      try {
        resetColorButton();
        btnSanPham.getStyleClass().add("nav-button-choose");
        setViewInvisible();
        sanPhamform.setVisible(true);
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/sanpham.fxml"));
        Parent productRoot = productLoader.load();
        addChildScene(sanPhamform, productRoot);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  public void loadUserImage(String maNV) {
    String anhNVPath = getAnhNV(maNV); // Lấy đường dẫn ảnh từ cơ sở dữ liệu
    System.out.println("Đường dẫn ảnh nhân viên: " + anhNVPath); // Ghi nhật ký
    try {
      if (anhNVPath != null && !anhNVPath.trim().isEmpty()) {
        String path = "file:/" + anhNVPath.replace("\\", "/");
        System.out.println(path);
        Image userImage = new Image(path, true);
        Image image = new Image(path, 36, 36, false, true);
        imgUser.setImage(image);
      } else {
        throw new Exception("Đường dẫn ảnh trống hoặc không hợp lệ.");
      }
    } catch (Exception e) {
      // Trường hợp không tải được ảnh, sử dụng ảnh mặc định
      System.err.println("Lỗi khi tải ảnh: " + e.getMessage());
      imgUser.setImage(new Image("file:../img/default.jpg", true));
    }
  }


  private String getAnhNV(String maNV) {
    Connection conn = DBUtils.openConnection();
    String sqlSelect = "SELECT anhNV FROM nhanvien WHERE tenNV = ?";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect)) {
      preparedStatement.setString(1, maNV); // Truyền giá trị maNV
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getString("anhNV"); // Lấy đường dẫn ảnh
        } else {
          System.err.println("Không tìm thấy nhân viên với maNV: " + maNV);
          return "../img/default.jpg"; // Ảnh mặc định
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Lỗi khi truy vấn ảnh nhân viên", e);
    } finally {
      DBUtils.closeConnection(conn);
    }
  }
}
