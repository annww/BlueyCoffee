package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.CTHD;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.SanPham;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class HoaDonController implements Initializable {
  @FXML
  private Button btnHuy;

  @FXML
  private Button btnThanhToan;

  @FXML
  private ComboBox<String> cbBoxThanhToan;

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
  private TableView<CTHD> menu_tableView;

  @FXML
  private TextField tienKhachTra;

  @FXML
  private TextField tienThua;

  @FXML
  private TextField tongTienSP;

  @FXML
  private TextField tongtienHD;

  private HashMap<String, String> loaiSPs = new HashMap<>();
  private PreparedStatement prepare;
  private Statement statement;
  private Connection conn;
  private ResultSet result;
  private ObservableList<SanPham> cardList = FXCollections.observableArrayList();
  public static List<CTHD> cthds = new ArrayList<>();
  private ObservableList<CTHD> cthdList= FXCollections.observableArrayList();
  private String[] loaiTT = new String[]{"Tiền mặt", "Chuyển khoản"};
  private Alert alert;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displayCardList(getCardListFromDB());
  }

  public ObservableList<SanPham> getCardListFromDB(){
    String sqlSelect = "SELECT * FROM sanpham WHERE `trangThai` = 1";
    ObservableList<SanPham> listData = FXCollections.observableArrayList();
    conn = DBUtils.openConnection();
    try{
      prepare = conn.prepareStatement(sqlSelect);
      result = prepare.executeQuery();
      SanPham sp;
      while(result.next()){
        sp = new SanPham(
            result.getString("maSP"),
            result.getNString("tenSP"),
            result.getString("anhSP"),
            result.getInt("donGia"),
            loaiSPs.get(result.getString("loaiSP"))
        );
        listData.add(sp);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return listData;
  }

  public void displayCardList(ObservableList<SanPham> observableList){
    cardList.clear();
    cardList.addAll(observableList);
    int row = 0;
    int column = 0;
    menu_gridPane.getRowConstraints().clear();
    menu_gridPane.getColumnConstraints().clear();
    for(int i = 0; i < cardList.size(); i++){
      try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/cardProduct.fxml"));
        AnchorPane pane = loader.load();
        cardProductController cardProduct = loader.getController();
        cardProduct.setData(cardList.get(i),this);
        if(column == 4){
          column = 0;
          row++;
        }
        menu_gridPane.add(pane, column++, row);
        GridPane.setMargin(pane, new Insets(10));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
