package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.CTHD;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.SanPham;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.AlertUtils;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.PriceUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
  private TableColumn<CTHD, String> menu_col_Note;

  @FXML
  private TableColumn<CTHD, Integer> menu_col_Price;

  @FXML
  private TableColumn<CTHD, Integer> menu_col_Quantity;

  @FXML
  private TableColumn<CTHD, Integer> menu_col_Total;

  @FXML
  private TableColumn<CTHD, String> menu_col_productName;

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
  private ObservableList<CTHD> cthdList = FXCollections.observableArrayList();
  private String[] loaiTT = new String[]{"Tiền mặt", "Chuyển khoản"};
  private Alert alert;
  private String maHD;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displayCardList(getCardListFromDB());
    ObservableList list = FXCollections.observableArrayList(loaiSPs.values());
    cardList = getCardListFromDB();
    showCTHDlist();
    getLoaiTT();
  }

  public ObservableList<SanPham> getCardListFromDB() {
    String sqlSelect = "SELECT * FROM sanpham WHERE `trangThai` = 1";
    ObservableList<SanPham> cardList = FXCollections.observableArrayList();
    conn = DBUtils.openConnection();
    try {
      prepare = conn.prepareStatement(sqlSelect);
      result = prepare.executeQuery();
      SanPham sp;
      while (result.next()) {
        sp = new SanPham(
            result.getString("maSP"),
            result.getNString("tenSP"),
            result.getString("anhSP"),
            result.getInt("donGia"),
            loaiSPs.get(result.getString("loaiSP")));
        cardList.add(sp);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    DBUtils.closeConnection(conn);
    return cardList;
  }

  public void displayCardList(ObservableList<SanPham> observableList) {
    cardList.clear();
    cardList.addAll(observableList);
    int row = 0;
    int column = 0;
    menu_gridPane.getRowConstraints().clear();
    menu_gridPane.getColumnConstraints().clear();
    for (int i = 0; i < cardList.size(); i++) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/daihocnhatrang/duongthianhhong/blueycoffee/fxml/cardProduct.fxml"));
        AnchorPane pane = loader.load();

        cardProductController cardProduct = loader.getController();
        cardProduct.setData(cardList.get(i), this);

        if (column == 4) {
          column = 0;
          row += 1;
        }

        menu_gridPane.add(pane, column++, row);
        GridPane.setMargin(pane, new Insets(10));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void clearTable() {
    menu_tableView.getItems().clear();
  }

  public void setHDInfor() {
    int tongTien = 0;
    for (CTHD cthd : cthds) {
      tongTien += cthd.getThanhTien();
    }
    tongtienHD.setText(String.valueOf(tongTien));
    tongTienSP.setText(String.valueOf(tongTien));
  }

  public void tinhtThua() {
    String input = tienKhachTra.getText();
    int tongTien = 0;
    for (CTHD cthd : cthds) {
      tongTien += cthd.getThanhTien();
    }
    int tienKhachTT = Integer.parseInt(input) - tongTien;
    tienThua.setText(String.valueOf(tienKhachTT));
  }

  public void showCTHDlist() {
    cthdList.clear();
    for (CTHD cthd : cthds) {
      cthdList.add(cthd);
    }
    setHDInfor();
    menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
    menu_col_Quantity.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
    menu_col_Price.setCellValueFactory(new PropertyValueFactory<>("donGia"));
    menu_col_Total.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
    menu_col_Note.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
    menu_tableView.setItems(cthdList);

    menu_col_Note.setCellFactory(TextFieldTableCell.forTableColumn());
    menu_col_Note.setOnEditCommit(event -> {
      CTHD cthd = event.getRowValue();
      cthd.setGhiChu(event.getNewValue());
    });

    menu_col_Quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    menu_col_Quantity.setOnEditCommit(event -> {
      CTHD cthd = event.getRowValue();
      cthd.setSoLuong(event.getNewValue());
      if (cthd.getSoLuong() == 0) {
        cthds.remove(cthd);
        cthdList.remove(cthd);
        System.out.println("Đã xóa sản phẩm này");
      }
      setHDInfor();
      menu_tableView.refresh();
    });

    menu_col_Total.setCellFactory(tc -> new TableCell<CTHD, Integer>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
        } else {
          setText(PriceUtils.formatPrice(item));
        }
      }
    });

    menu_col_Price.setCellFactory(tc -> new TableCell<CTHD, Integer>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
        } else {
          setText(PriceUtils.formatPrice(item));
        }
      }
    });
  }

  private void getLoaiTT() {
    List<String> listTT = new ArrayList<>();
    for (String tt : loaiTT) {
      listTT.add(tt);
    }
    ObservableList list = FXCollections.observableArrayList(listTT);
    cbBoxThanhToan.setItems(list);
  }

  public void clearHD() {
    cthds.clear();
    cthdList.clear();
    menu_tableView.getItems().clear();
    tongtienHD.setText(null);
    tongTienSP.setText(null);
    cbBoxThanhToan.getSelectionModel().select(null);
    tienKhachTra.setText(null);
    tienThua.setText(null);
    displayCardList(getCardListFromDB());
  }

  public void taoHD() {
    if (tienKhachTra.getText().isEmpty() || cbBoxThanhToan.getValue().isEmpty()) {
      AlertUtils.setAlert(Alert.AlertType.ERROR, "Lỗi", "Hãy điền đủ thông tin hóa đơn");
    } else {
      maHD = taoMaHD();
      String maNV = Current_data.userid;
      System.out.println(maHD);
      conn = DBUtils.openConnection();
      String sqlInsertHD = "INSERT INTO `hoadon`(`maHD`, `nguoiTao`, `tongTien`,`thanhToan`, `ghiChu`) VALUES (?,?,?,?,?)";
      try {
        prepare = conn.prepareStatement(sqlInsertHD);
        prepare.setString(1, maHD);
        prepare.setString(2, maNV);
        prepare.setInt(3, Integer.parseInt(tongtienHD.getText()));
        prepare.setString(4, cbBoxThanhToan.getValue());
        prepare.setString(5, "");
        int rowsInserted = prepare.executeUpdate();
        if (rowsInserted > 0) {
          System.out.println("Thêm hóa đơn thành công");
        } else {
          System.out.println("Không thể thêm hóa đơn");
        }
        taoCTHD(maHD);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      } finally {
        DBUtils.closeConnection(conn);
        clearHD();
      }
    }

  }

  private String taoMaHD() {
    String maHD = "HD";
    LocalDate currentDate = LocalDate.now();
    System.out.println(currentDate);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
    String ngayTao = currentDate.format(formatter);
    maHD += ngayTao;
    String sqlSelect = "SELECT * from hoadon WHERE DATE(`createdAt`) = ? ORDER BY `maHD` DESC LIMIT 1";
    conn = DBUtils.openConnection();
    try (PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect)) {
      preparedStatement.setString(1, String.valueOf(currentDate));
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (!resultSet.next()) {
          return maHD + "0001";
        }
        String lastHD = resultSet.getString("maHD");
        System.out.println("Mã lớn nhất: " + lastHD);
        int number = Integer.parseInt(lastHD.substring(maHD.length()));
        return maHD + String.format("%04d", number + 1);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      DBUtils.closeConnection(conn);
    }
  }


  private void taoCTHD(String maHD) throws SQLException {
    String sqlInsertCTHD = "INSERT INTO `cthd`(`maHD`, `maSP`, `donGia` ,`soLuong`, `thanhTien`, `ghiChu`) VALUES (?,?,?,?,?,?)";
    for (CTHD cthd : cthds) {
      prepare = conn.prepareStatement(sqlInsertCTHD);
      prepare.setString(1, maHD);
      prepare.setString(2, cthd.getMaSP());
      prepare.setInt(3, cthd.getDonGia());
      prepare.setInt(4, cthd.getSoLuong());
      prepare.setInt(5, cthd.getThanhTien());
      prepare.setString(6, cthd.getGhiChu());
      prepare.executeUpdate();
    }
  }
}
