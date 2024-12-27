package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
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
  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
