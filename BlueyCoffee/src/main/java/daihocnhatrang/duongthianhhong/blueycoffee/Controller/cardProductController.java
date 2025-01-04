package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.CTHD;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.SanPham;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class cardProductController implements Initializable {
  @FXML
  private AnchorPane product_Form;

  @FXML
  private ImageView product_ImageView;

  @FXML
  private Label product_Name;

  @FXML
  private Label product_Price;

  @FXML
  private Spinner<Integer> product_Spineer;

  @FXML
  private Button product_btnThem;

  private SpinnerValueFactory<Integer> spin;
  private SanPham sanPham;
  private BanHangController banHang;
  private Image image;
  private Connection conn;
  private PreparedStatement prepare;
  private Statement statement;
  private ResultSet result;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

  public void setData(SanPham sanPham, BanHangController banHang) {
    this.banHang = banHang;
    this.sanPham = sanPham;

    product_Name.setText(sanPham.getTenSP());
    product_Price.setText(String.valueOf(sanPham.getDonGia()));

    String path = "file:/" + sanPham.getAnhSP().replace("\\", "/");

    try {
      image = new Image(path, 124, 124, false, false);
      if (image.isError()) {
        throw new Exception();
      }
      product_ImageView.setImage(image);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addBtn(javafx.scene.input.MouseEvent mouseEvent) {
    boolean exists = false;

    for (CTHD cthd : BanHangController.cthds) {
      if (sanPham.getMaSP().equals(cthd.getMaSP())) {
        exists = true;
        break;
      }
    }
    if (!exists) {
      BanHangController.cthds.add(new CTHD(sanPham.getMaSP(), sanPham.getTenSP(), "", sanPham.getDonGia(), 1));
      banHang.clearTable();
      banHang.showCTHDlist();
    }
  }
  }

