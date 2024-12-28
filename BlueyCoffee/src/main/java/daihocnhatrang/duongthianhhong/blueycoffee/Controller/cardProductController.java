package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

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
  private Spinner<Integer> product_Spineer;

  @FXML
  private Button product_btnThem;

  private SpinnerValueFactory<Integer> spin;
  private SanPham sanPham;
  private HoaDonController hoaDon;
  private Image image;
  private Connection conn;
  private PreparedStatement prepare;
  private Statement statement;
  private ResultSet result;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setQuantity();
  }

  public void setData(SanPham sanPham, HoaDonController hoaDon) {
    this.hoaDon = hoaDon;
    this.sanPham = sanPham;

    product_Name.setText(sanPham.getTenSP());
    product_Price.setText(String.valueOf(sanPham.getDonGia()));

    String path = "file:/" + sanPham.getAnhSP().replace("\\", "/");
    System.out.println("Đường dẫn ảnh: " + path);

    try {
      image = new Image(path, 124, 124, false, false);
      if (image.isError()) {
        throw new Exception("Lỗi khi tải ảnh.");
      }
      product_ImageView.setImage(image);
      System.out.println("Ảnh đã được hiển thị.");
    } catch (Exception e) {
      System.out.println("Không thể tải ảnh từ đường dẫn: " + path);
      e.printStackTrace();
    }
  }



  public void checkImageLoaded() {
    if (product_ImageView.getImage() != null) {
      System.out.println("Ảnh đã được tải thành công: " + sanPham.getAnhSP());
    } else {
      System.out.println("Không thể tải ảnh từ đường dẫn: " + sanPham.getAnhSP());
    }
  }

  private int qty;
  public void setQuantity(){
    spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
    product_Spineer.setValueFactory(spin);
  }
//  public void addCTHD(MouseEvent mouseEvent){
//    HoaDonController.cthds.add(new CTHD(sanPham.getMaSP(),sanPham.getTenSP()," ", sanPham.getDonGia(), 1));
//    hoaDon.clearTable();
//    hoaDon.showCTHDList();
//  }
}
