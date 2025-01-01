package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.NhanVien;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static daihocnhatrang.duongthianhhong.blueycoffee.Utils.AlertUtils.setAlert;

public class NhanVienController implements Initializable {

  @FXML
  private ImageView img;

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
  private ComboBox<String> cbbChucVuFind;

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

  @FXML
  private AnchorPane nhanVien;

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
    ToggleGroup genderGroup = new ToggleGroup();
    radioNam.setToggleGroup(genderGroup);
    radioNu.setToggleGroup(genderGroup);

    radioNam.setSelected(true);
    hienThiCVTrangThai();
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
      ObservableList list = FXCollections.observableArrayList(loaiCVs.values());
      cbbChucVuFind.setItems(list);
      cbbChucVu.setItems(list);
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

  public void hienThiCVTrangThai() {
      List<String> listTT = new ArrayList<>();
      for(String trangthai : trangthainvs){
        listTT.add(trangthai);
      }
      ObservableList listTrangThai = FXCollections.observableArrayList(listTT);
      cbbTrangThai.setItems(listTrangThai);
      cbbTrangThai.getSelectionModel().select(0);
      cbbTrangThaiFind.setItems(listTrangThai);
  }

  public void importImage() {
    FileChooser openFile = new FileChooser();
    openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));
    File file = openFile.showOpenDialog(nhanVien.getScene().getWindow());
    if (file != null) {
      Current_data.path = file.getAbsolutePath();
      Image imgage = new Image(file.toURI().toString(), 125, 125, false, true);
      img.setImage(imgage);
    }
  }


  public void displaySelectedNV(){
    NhanVien selectedItem = tableView_ttinNV.getSelectionModel().getSelectedItem();
    int num = tableView_ttinNV.getSelectionModel().getSelectedIndex();
    if((num - 1) < -1 ) return;
    txtMaNV.setText(selectedItem.getMaNV());
    txtTenNV.setText(selectedItem.getTenNV());
    if(selectedItem.isGioiTinh()){
      radioNam.setSelected(true);
      radioNu.setSelected(false);
    }
    else {
      radioNu.setSelected(true);
      radioNam.setSelected(false);
    }
    String getDate = String.valueOf(selectedItem.getNgaySinh());
    LocalDate parse = LocalDate.parse(getDate);
    System.out.println(parse);
    dateNgaySinh.setValue(parse);
    txtEmail.setText(selectedItem.getEmail());
    txtPhoneNum.setText(selectedItem.getSDT());
    Current_data.path = selectedItem.getAnhNV();
    Current_data.id = selectedItem.getMaNV();
    String path = "File:"+ Current_data.path;
    Image image = new Image(path, 113, 125, false, true);
    img.setImage(image);
    String cvNV= selectedItem.getChucVu();
    cbbChucVu.getSelectionModel().select(cvNV);
    cbbTrangThai.getSelectionModel().select(selectedItem.getIsWorking()=="Đang làm"?0:1);
  }

  public int getMaCV(ComboBox<String> cbbChucVu) {
    String selectedLoai = cbbChucVu.getSelectionModel().getSelectedItem();
    for (int key : loaiCVs.keySet()) {
      if (loaiCVs.get(key).equals(selectedLoai)) {
        return key;
      }
    }
    return 0;
  }


  public void reloadNV() {
    txtMaNV.setText("");
    txtTenNV.setText("");
    radioNam.setSelected(false);
    radioNu.setSelected(false);
    dateNgaySinh.setValue(null);
    txtEmail.setText("");
    txtPhoneNum.setText("");
    cbbChucVu.setValue(null);
    cbbTrangThai.setValue(null);
    img.setImage(null);
    txtTenNVFind.setText("");
    cbbChucVuFind.setValue(null);
    cbbTrangThaiFind.setValue(null);

    Current_data.id = "";
    ObservableList<NhanVien> nhanViens = getNhanViens("SELECT * FROM nhanvien");
    tableView_ttinNV.setItems(nhanViens);
  }

  public boolean setRadioGender() {
    return radioNam.isSelected();
  }

  public void deleteNV() {
    if (Current_data.id == null || Current_data.id.isEmpty()) {
      setAlert(Alert.AlertType.ERROR, "Lỗi", "Hãy chọn nhân viên cần xóa!");
      return;
    }

    Optional<ButtonType> optional = setAlert(Alert.AlertType.CONFIRMATION, "Xác nhận", "Bạn muốn xóa nhân viên này?");
    if (optional.isPresent() && optional.get().equals(ButtonType.OK)) {
      String sqlDelete = "DELETE FROM nhanvien WHERE maNV = ?";
      try {
        conn = DBUtils.openConnection();
        prepare = conn.prepareStatement(sqlDelete);
        prepare.setString(1, Current_data.id);
        int rowsAffected = prepare.executeUpdate();

        if (rowsAffected > 0) {
          setAlert(Alert.AlertType.INFORMATION, "Thông tin", "Đã xóa nhân viên này!");
        } else {
          setAlert(Alert.AlertType.WARNING, "Thông tin", "Không tìm thấy nhân viên để xóa!");
        }

      } catch (SQLException e) {
        setAlert(Alert.AlertType.ERROR, "Lỗi", "Đã xảy ra lỗi khi xóa nhân viên: " + e.getMessage());
        e.printStackTrace();
      } finally {
        DBUtils.closeConnection(conn);
      }

      reloadNV();
    }
  }
  public void addNV() {
    if (txtTenNV.getText().isEmpty() ||
        (!radioNam.isSelected() && !radioNu.isSelected()) ||
        dateNgaySinh.getValue() == null || txtEmail.getText().isEmpty() ||
        txtPhoneNum.getText().isEmpty() ||
        Current_data.path == null) {
      setAlert(Alert.AlertType.ERROR, "Lỗi", "Hãy nhập đủ thông tin nhân viên!");
      return;
    }

    try {
      String tenNV = txtTenNV.getText();
      boolean gioiTinh = setRadioGender();
      Date ngaySinh = Date.valueOf(dateNgaySinh.getValue());
      int maChucVu = getMaCV(cbbChucVu);
      String sdt = txtPhoneNum.getText();
      String email = txtEmail.getText();
      String path = Current_data.path.replace("\\", "\\\\");
      boolean isWorking = cbbTrangThai.getSelectionModel().getSelectedIndex() == 0;

      String sqlInsert = "INSERT INTO nhanvien ( tenNV, gioiTinh, ngaySinh, chucVu, SDT, email, anhNV, isWorking, username, password) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      conn = DBUtils.openConnection();
      prepare = conn.prepareStatement(sqlInsert);
      prepare.setString(1, tenNV);
      prepare.setBoolean(2, gioiTinh);
      prepare.setDate(3, ngaySinh);
      prepare.setInt(4, maChucVu);
      prepare.setString(5, sdt);
      prepare.setString(6, email);
      prepare.setString(7, path);
      prepare.setBoolean(8, isWorking);
      prepare.setString(9, email);
      prepare.setString(10, sdt);

      int rowsAffected = prepare.executeUpdate();
      if (rowsAffected > 0) {
        setAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm nhân viên mới thành công!");
      }

      reloadNV();
    } catch (SQLException e) {
      setAlert(Alert.AlertType.ERROR, "Lỗi", "Đã xảy ra lỗi khi thêm nhân viên: " + e.getMessage());
      e.printStackTrace();
    } finally {
      DBUtils.closeConnection(conn);
    }
  }
  public void updateNV(){
    
  }
}
