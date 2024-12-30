package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.BLL.NhanVienBLL;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.NhanVien;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class NhanVienController {

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
  private TableView<?> tableView_ttinNV;

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

}
