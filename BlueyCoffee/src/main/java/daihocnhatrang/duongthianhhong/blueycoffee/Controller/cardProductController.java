package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.SanPham;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.events.MouseEvent;

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
  private Spinner<?> product_Spineer;

  @FXML
  private Button product_btnThem;

  private SanPham sanPham;
  private HoaDonController hoaDon;
  private Image image;
  private Connection conn;
  private PreparedStatement prepare;
  private Statement statement;
  private ResultSet result;
  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void setData(SanPham sanPham, HoaDonController hoaDon){
    this.hoaDon = hoaDon;
    this.sanPham = sanPham;
    product_Name.setText(sanPham.getTenSP());
    product_Price.setText(String.valueOf(sanPham.getDonGia()));
    String path = "File: " + sanPham.getAnhSP();
    image = new Image(path, 124, 124,false, false);
    product_ImageView.setImage(image);
  }

//  public void addCTHD(MouseEvent mouseEvent){
//    HoaDonController.cthds.add(new CTHD(sanPham.getMaSP(),sanPham.getTenSP()," ", sanPham.getDonGia(), 1));
//    hoaDon.clearTable();
//    hoaDon.showCTHDList();
//  }
}
