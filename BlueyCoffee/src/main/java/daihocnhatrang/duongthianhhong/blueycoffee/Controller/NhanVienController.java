package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.NhanVien;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.ResourceBundle;

import static daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data.chucVu;

public class NhanVienController implements Initializable {

  @FXML
  private Button btnAdd;

  @FXML
  private Button btnChangeImg;

  @FXML
  private Button btnDelete;

  @FXML
  private Button btnEdit;

  @FXML
  private Button btnFind;

  @FXML
  private Button btnUpdate;

  @FXML
  private ComboBox<String> cbbChucVu;

  @FXML
  private ComboBox<String> cbbTrangThai;

  @FXML
  private ComboBox<String> cbbTrangThaiFind;

  @FXML
  private ComboBox<String> ccbChucVuFind;

  @FXML
  private TableColumn<NhanVien, String> col_ChucVu;

  @FXML
  private TableColumn<NhanVien, String> col_GioiTinh;

  @FXML
  private TableColumn<NhanVien, String> col_NgayBatDauLam;

  @FXML
  private TableColumn<NhanVien, String> col_TrangThai;

  @FXML
  private TableColumn<NhanVien, String> col_maNV;

  @FXML
  private TableColumn<NhanVien, String> col_tenNV;

  @FXML
  private DatePicker dateNgaySinh;

  @FXML
  private RadioButton radioNam;

  @FXML
  private RadioButton radioNu;

  @FXML
  private TableView<NhanVien> tableView_ttinNV;

  @FXML
  private TextField txtEmail;

  @FXML
  private TextField txtMaNV;

  @FXML
  private TextField txtPhoneNum;

  @FXML
  private TextField txtTenNV;

  @FXML
  private TextField txtTenNVFind;

  private HashMap<String, String> loainvs = new HashMap<>();
  private String[] trangthainvs = new String[]{"Đang làm", "Nghỉ làm"};
  private Connection conn;
  private PreparedStatement prepare;
  private Statement statement;
  private ResultSet result;
  private ObservableList<NhanVien> nhanViens;
  private Image image;
  private HashMap<Integer, String> loaiCVs = new HashMap<>();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    getCVfromDB();
    hienThiNV();

  }

  public void getCVfromDB(){
    conn = DBUtils.openConnection();
    String sqlSelect = "SELECT * FROM chucVu";
    statement = null;
    try{
      statement = conn.createStatement();
      result = statement.executeQuery(sqlSelect);
      while (result.next()){
        Integer maCV = result.getInt("maCV");
        String loaiCV = result.getString("loaiChucVu");
        loaiCVs.put(maCV, loaiCV);
      }
      System.out.println(loaiCVs.values());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    DBUtils.closeConnection(conn);
  }

  public ObservableList<NhanVien> getNhanViens(String sql) {
    ObservableList<NhanVien> nvList = FXCollections.observableArrayList();
    conn = DBUtils.openConnection();
    try {
      prepare = conn.prepareStatement(sql);
      result = prepare.executeQuery();

      while (result.next()) {
        NhanVien nv = new NhanVien(
            result.getString("maNV"),
            result.getNString("tenNV"),
            result.getBoolean("gioiTinh"),
            result.getDate("ngaySinh"),
            loaiCVs.get(result.getInt("chucVu")),
            result.getNString("SDT"),
            result.getNString("email"),
            result.getString("anhNV"),
            result.getBoolean("isWorking") ? "Đang làm" : "Nghỉ làm",
            result.getString("username"),
            result.getString("password"),
            result.getTimestamp("createdAt"),
            null
        );
        System.out.println(loaiCVs.get(1));
        System.out.println(nv.getChucVu());
        nvList.add(nv);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtils.closeConnection(conn);
    }
    return nvList;
  }


  public void hienThiNV() {
    ObservableList<NhanVien> nhanViens = getNhanViens("SELECT * FROM nhanvien");

    // Kiểm tra danh sách dữ liệu
    System.out.println("Danh sách nhân viên: " + nhanViens.size());
    for (NhanVien nv : nhanViens) {
      System.out.println(nv.getTenNV());
    }

    col_maNV.setCellValueFactory(new PropertyValueFactory<>("maNV"));
    col_tenNV.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
    col_GioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinhText"));
    col_ChucVu.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
    col_TrangThai.setCellValueFactory(new PropertyValueFactory<>("isWorking"));
    col_NgayBatDauLam.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

    tableView_ttinNV.setItems(nhanViens);
  }

//  private String getMaCV(){
//    String maCV = null;
//    for(String key: loaiCVs.keySet()){
//      System.out.println(key);
//    }
//  }

//  private String getLoaiChucVu(String maCV) {
//    String loaiChucVu = "";
//
//    // Kiểm tra mã chức vụ không rỗng
//    if (maCV == null || maCV.isEmpty()) {
//      System.out.println("Mã chức vụ không hợp lệ.");
//      return loaiChucVu;
//    }
//
//    String query = "SELECT loaiCV FROM chucvu WHERE maCV = ?";
//    try (Connection conn = DBUtils.openConnection();
//         PreparedStatement prepare = conn.prepareStatement(query)) {
//
//      // Gán tham số vào câu lệnh SQL
//      prepare.setString(1, maCV);
//
//      // Thực thi câu truy vấn
//      ResultSet rs = prepare.executeQuery();
//      if (rs.next()) {
//        loaiChucVu = rs.getString("loaiCV");
//      } else {
//        System.out.println("Không tìm thấy chức vụ với mã: " + maCV);
//      }
//
//    } catch (SQLException e) {
//      System.err.println("Lỗi khi truy vấn loại chức vụ: " + e.getMessage());
//    }
//
//    return loaiChucVu;
//  }



}
