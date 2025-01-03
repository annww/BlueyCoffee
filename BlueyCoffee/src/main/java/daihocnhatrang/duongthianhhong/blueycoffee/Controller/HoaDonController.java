package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.CTHD;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.HoaDon;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.PriceUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class HoaDonController implements Initializable {
  @FXML
  private Button btnFind;

  @FXML
  private TableColumn<CTHD, Integer> col_DonGia;

  @FXML
  private TableColumn<HoaDon, String> col_HDNote;

  @FXML
  private TableColumn<HoaDon, Integer> col_PTTT;

  @FXML
  private TableColumn<CTHD, Integer> col_SoLuong;

  @FXML
  private TableColumn<CTHD, String> col_ghiChu;

  @FXML
  private TableColumn<HoaDon, String> col_maHD;

  @FXML
  private TableColumn<CTHD, String> col_maSP;

  @FXML
  private TableColumn<HoaDon, String> col_nguoiTao;

  @FXML
  private TableColumn<CTHD, String> col_tenSP;

  @FXML
  private TableColumn<CTHD, Integer> col_thanhTien;

  @FXML
  private TableColumn<HoaDon, Timestamp> col_thoiGianTao;

  @FXML
  private TableColumn<HoaDon, Integer> col_tongTien;

  @FXML
  private DatePicker dateFromDate;

  @FXML
  private DatePicker dateToDate;

  @FXML
  private Label lbLoaiTT;

  @FXML
  private Label lbMaHD;

  @FXML
  private Label lbNguoiTao;

  @FXML
  private Label lbTimeCreated;

  @FXML
  private TextField tFNote;

  @FXML
  private TableView<CTHD> tbView_CTHD;

  @FXML
  private TableView<HoaDon> tbView_HoaDon;

  @FXML
  private TableColumn<HoaDon, Timestamp> col_thoiGianXacNhan;

  @FXML
  private TextField txtMaHD;

  private PreparedStatement prepare;
  private String sqlSelectOrder = "SELECT * FROM hoadon";
  private String sqlSelectWaiting = "SELECT * FROM hoadon WHERE `trangThai` = 1";
  private ObservableList<HoaDon> hoaDons;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    showHoaDonList(sqlSelectOrder, null);
    tbView_HoaDon.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        showCTHDtable();
        showdetailCTHD(sqlSelectWaiting);
      }
    });
  }

  public ObservableList<HoaDon> getOrderL(String sql) {
    ObservableList<HoaDon> hoaDonList = FXCollections.observableArrayList();
    try (Connection conn = DBUtils.openConnection();
         PreparedStatement prepare = conn.prepareStatement(sql);
         ResultSet result = prepare.executeQuery()) {

      while (result.next()) {
        HoaDon hoaDon = new HoaDon(
            result.getString("maHD"),
            result.getString("nguoiTao"),
            result.getTimestamp("createdAt"),
            result.getTimestamp("confirmAt"),
            result.getInt("tongTien"),
            result.getString("thanhToan"),
            result.getString("ghiChu")
        );
        hoaDonList.add(hoaDon);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return hoaDonList;
  }

  public void showHoaDonList(String sql, ObservableList<HoaDon> hoaDons) {
    if (hoaDons == null) {
      hoaDons = getOrderL(sql);
    }

    col_maHD.setCellValueFactory(new PropertyValueFactory<>("maHD"));
    col_nguoiTao.setCellValueFactory(new PropertyValueFactory<>("nguoiTao"));
    col_thoiGianTao.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    col_thoiGianXacNhan.setCellValueFactory(new PropertyValueFactory<>("confirmAt"));
    col_tongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
    col_PTTT.setCellValueFactory(new PropertyValueFactory<>("thanhToan"));
    col_ghiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));

    col_tongTien.setCellFactory(tc -> new TableCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? null : PriceUtils.formatPrice(item));
      }
    });

    tbView_HoaDon.setItems(hoaDons);
  }

  public ObservableList<CTHD> getCTHDList(String maHD) {
    ObservableList<CTHD> cthdList = FXCollections.observableArrayList();
    String sql = "SELECT * FROM cthd WHERE maHD = ?";
    try (Connection conn = DBUtils.openConnection();
         PreparedStatement prepare = conn.prepareStatement(sql)) {
      prepare.setString(1, maHD);
      try (ResultSet result = prepare.executeQuery()) {
        while (result.next()) {
          CTHD cthd = new CTHD(
              result.getString("maHD"),
              result.getString("maSP"),
              result.getString("tenSP"),
              result.getInt("donGia"),
              result.getInt("soLuong"),
              result.getString("ghiChu")
          );
          cthdList.add(cthd);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return cthdList;
  }

  public void showCTHDtable() {
    HoaDon selectedHoaDon = tbView_HoaDon.getSelectionModel().getSelectedItem();
    if (selectedHoaDon == null) {
      return;
    }

    String maHD = selectedHoaDon.getMaHD();
    ObservableList<CTHD> cthds = getCTHDList(maHD);

    col_maSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
    col_tenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
    col_SoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
    col_DonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
    col_thanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
    col_ghiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));

    col_DonGia.setCellFactory(tc -> new TableCell<CTHD, Integer>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? null : PriceUtils.formatPrice(item));
      }
    });

    col_thanhTien.setCellFactory(tc -> new TableCell<CTHD, Integer>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? null : PriceUtils.formatPrice(item));
      }
    });

    tbView_CTHD.setItems(cthds);
  }

  public void showdetailCTHD(String sql) {
    ObservableList<HoaDon> hoaDons = getOrderL(sql);

    if (hoaDons.isEmpty()) {
      return;
    }

    HoaDon selectedHoaDon = hoaDons.get(0);

    lbMaHD.setText(selectedHoaDon.getMaHD());
    lbNguoiTao.setText(selectedHoaDon.getNguoiTao());
    lbTimeCreated.setText(String.valueOf(selectedHoaDon.getCreatedAt()));
    lbLoaiTT.setText(selectedHoaDon.getThanhToan());
    tFNote.setText(selectedHoaDon.getGhiChu());

    col_maHD.setCellValueFactory(new PropertyValueFactory<>("maHD"));
    col_nguoiTao.setCellValueFactory(new PropertyValueFactory<>("nguoiTao"));
    col_thoiGianTao.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    col_tongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
    col_HDNote.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
    col_PTTT.setCellValueFactory(new PropertyValueFactory<>("thanhToan"));

    col_tongTien.setCellFactory(tc -> new TableCell<HoaDon, Integer>() {
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

    tbView_HoaDon.setItems(hoaDons);
  }

  public void findBtn() {
    String maHD = txtMaHD.getText().trim();
    LocalDate dateStart = dateFromDate.getValue();
    LocalDate dateEnd = dateToDate.getValue();

    if (maHD.isEmpty() && dateStart == null && dateEnd == null) {
      showHoaDonList(sqlSelectOrder, null); // Hiển thị toàn bộ danh sách
      return;
    }

    // lọc lại ds từ csdl
    StringBuilder query = new StringBuilder("SELECT * FROM hoadon WHERE 1=1");
    if (!maHD.isEmpty()) {
      query.append(" AND maHD LIKE ?");
    }
    if (dateStart != null) {
      query.append(" AND createdAt >= ?");
    }
    if (dateEnd != null) {
      query.append(" AND createdAt <= ?");
    }

    ObservableList<HoaDon> hoaDonsF = FXCollections.observableArrayList();
    try (Connection conn = DBUtils.openConnection();
         PreparedStatement prepare = conn.prepareStatement(query.toString())) {
      int paramIndex = 1;
      if (!maHD.isEmpty()) {
        prepare.setString(paramIndex++, "%" + maHD + "%");
      }
      if (dateStart != null) {
        prepare.setDate(paramIndex++, Date.valueOf(dateStart));
      }
      if (dateEnd != null) {
        prepare.setDate(paramIndex++, Date.valueOf(dateEnd));
      }

      try (ResultSet result = prepare.executeQuery()) {
        while (result.next()) {
          HoaDon hoaDon = new HoaDon(
              result.getString("maHD"),
              result.getString("nguoiTao"),
              result.getTimestamp("createdAt"),
              result.getTimestamp("confirmAt"),
              result.getInt("tongTien"),
              result.getString("thanhToan"),
              result.getString("ghiChu")
          );
          hoaDonsF.add(hoaDon);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    showHoaDonList(null, hoaDonsF);
  }
}

