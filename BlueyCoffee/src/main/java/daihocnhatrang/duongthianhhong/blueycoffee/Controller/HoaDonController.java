package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.SanPham;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.Connection;

public class HoaDonController {
  @FXML
  private Button btnHuy;

  @FXML
  private Button btnThanhToan;

  @FXML
  private ComboBox<?> cbBoxThanhToan;

  @FXML
  private TableColumn<?, ?> menu_col_Price;

  @FXML
  private TableColumn<?, ?> menu_col_Price1;

  @FXML
  private TableColumn<?, ?> menu_col_Price11;

  @FXML
  private TableColumn<?, ?> menu_col_Quantity;

  @FXML
  private TableColumn<?, ?> menu_col_productName;

  @FXML
  private GridPane menu_gridPane;

  @FXML
  private ScrollPane menu_scrollPane;

  @FXML
  private TableView<?> menu_tableView;

  @FXML
  private TextField tienKhachTra;

  @FXML
  private TextField tienThua;

  @FXML
  private TextField tongTienSP;

  @FXML
  private TextField tongtienHD;

  private Connection conn;
  private ObservableList<SanPham> cardList = FXCollections.observableArrayList();
  private String[] loaiTT = new String[]{"Tiền mặt", "Chuyển khoản"};
}
