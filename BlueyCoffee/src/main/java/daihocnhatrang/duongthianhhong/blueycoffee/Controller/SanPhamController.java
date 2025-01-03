package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.Current_data;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.SanPham;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.PriceUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.*;

import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class SanPhamController implements Initializable {
  @FXML
  private Button btnAdd;

  @FXML
  private Button btnCancel;

  @FXML
  private Button btnDelete;

  @FXML
  private Button btnImporImg;

  @FXML
  private Button btnUpdate;

  @FXML
  private TextField donGia;

  @FXML
  private ComboBox<String> loaiSP;

  @FXML
  private TextField maSP;

  @FXML
  private TextField tenSP;

  @FXML
  private ComboBox<String> trangThai;

  @FXML
  private ComboBox<String> getLoaiSP;

  @FXML
  private TableColumn<SanPham, Integer> colDonGia;

  @FXML
  private TableColumn<SanPham, String> colGhiChu;

  @FXML
  private TableColumn<SanPham, String> colLoaiSP;

  @FXML
  private TableColumn<SanPham, String> colMaSP;

  @FXML
  private TableColumn<SanPham, String> colTenSP;

  @FXML
  private TableColumn<SanPham, String> colTrangThai;

  @FXML
  private TableView<SanPham> tableSP;

  @FXML
  private ImageView img;

  @FXML
  private AnchorPane sanPham;

  private HashMap<String, String> loaisps = new HashMap<>();
  private Connection conn;
  private PreparedStatement prepare;
  private Statement statement;
  private ResultSet result;

  private ObservableList<SanPham> sanPhams = FXCollections.observableArrayList();
  private Alert alert;
  private String[] trangthaisps = new String[]{"Đang bán", "Ngưng bán"};

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    getSPFromDB();
    getTrangThai();
    showSPList("SELECT * FROM sanpham");

    tableSP.setOnMouseClicked(event -> displaySelectedSP());
  }

  private void displaySelectedSP() {
    SanPham selectedSP = tableSP.getSelectionModel().getSelectedItem();
    if (selectedSP != null) {
      maSP.setText(selectedSP.getMaSP());
      tenSP.setText(selectedSP.getTenSP());
      donGia.setText(String.valueOf(selectedSP.getDonGia()));
      loaiSP.setValue(selectedSP.getLoaiSP());
      trangThai.setValue(selectedSP.getTrangThai());

      // Hiển thị ảnh sản phẩm
      String imagePath = selectedSP.getAnhSP();
      if (imagePath != null && !imagePath.isEmpty()) {
        File file = new File(imagePath);
        if (file.exists()) {
          img.setImage(new Image(file.toURI().toString(), 113, 125, false, true));
        } else {
          img.setImage(null); // Đặt về null nếu không tìm thấy ảnh
        }
      } else {
        img.setImage(null);
      }

      // Lưu mã sản phẩm vào Current_data.id (để dùng trong chức năng xóa hoặc cập nhật)
      Current_data.id = selectedSP.getMaSP();
    }
  }

  public void showSPList(String sql) {
    sanPhams = getSPList(sql);
    colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
    colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
    colLoaiSP.setCellValueFactory(new PropertyValueFactory<>("loaiSP"));
    colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
    colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
    colDonGia.setCellFactory(tc -> new TableCell<SanPham, Integer>() {
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
    tableSP.setItems(sanPhams);
  }

  public ObservableList<SanPham> getSPList(String sql) {
    ObservableList<SanPham> spList = FXCollections.observableArrayList();
    conn = DBUtils.openConnection();
    String sqlSelect = sql;
    try {
      prepare = conn.prepareStatement(sqlSelect);
      result = prepare.executeQuery(sqlSelect);
      SanPham sp;
      while (result.next()) {
        sp = new SanPham(
            result.getString("maSP"),
            result.getNString("tenSP"),
            loaisps.get(result.getString("loaiSP")),
            result.getString("anhSP"),
            result.getNString("moTa"),
            result.getNString("ghiChu"),
            result.getBoolean("trangThai") ? "Đang bán" : "Ngừng bán",
            result.getInt("donGia")
        );
        spList.add(sp);
      }
      tableSP.setItems(spList);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    DBUtils.closeConnection(conn);
    return spList;
  }

  private void getSPFromDB() {
    conn = DBUtils.openConnection();
    String sqlSelect = "SELECT * FROM loaisp";
    Statement lenh = null;
    try {
      lenh = conn.createStatement();
      ResultSet ketQua = lenh.executeQuery(sqlSelect);
      while (ketQua.next()) {
        String maLoai = ketQua.getString("maLSP");
        String tenLoai = ketQua.getString("tenLSP");
        loaisps.put(maLoai, tenLoai);
      }
      ObservableList list = FXCollections.observableArrayList(loaisps.values());
      loaiSP.setItems(list);
      loaiSP.getSelectionModel().select(0);
      trangThai.setItems(list);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    DBUtils.closeConnection(conn);
  }

  private void getTrangThai() {
    List<String> listTT = new ArrayList<>();
    for (String trangthai : trangthaisps) {
      listTT.add(trangthai);
    }
    ObservableList list = FXCollections.observableArrayList(listTT);
    trangThai.setItems(list);
    trangThai.getSelectionModel().select(0);
  }

  public void reloadSP() {
    maSP.setText("");
    tenSP.setText("");
    donGia.setText("");
    img.setImage(null);
    loaiSP.setValue(null);
    trangThai.setValue(null);
    Current_data.id = "";
    showSPList("SELECT * FROM sanpham");
  }


  public void importImage() {
    FileChooser openFile = new FileChooser();
    openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));
    File file = openFile.showOpenDialog(sanPham.getScene().getWindow());
    if (file != null) {
      Current_data.path = file.getAbsolutePath();
      Image imgage = new Image(file.toURI().toString(), 113, 125, false, true);
      img.setImage(imgage);
    }
  }


  private String setMaSP(ComboBox<String> loaiSP) {
    String getMaLSP = getMaLSP(this.loaiSP);
    conn = DBUtils.openConnection();
    String sqlSelect = "SELECT maSP FROM sanpham WHERE loaiSP LIKE ? ORDER BY maSP DESC LIMIT 1";
    try (PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect)) {
      preparedStatement.setString(1, getMaLSP + "%");
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (!resultSet.next()) {
          return getMaLSP + "0001";
        }
        String lastMaNV = resultSet.getString("maSP");
        System.out.println("Mã lớn nhất: " + lastMaNV);
        int number = Integer.parseInt(lastMaNV.substring(getMaLSP.length()));
        return getMaLSP + String.format("%04d", number + 1);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      DBUtils.closeConnection(conn);
    }
  }

  private String getMaLSP(ComboBox<String> cbb) {
    String maLSP = null;
    for (String key : loaisps.keySet()) {
      if (loaisps.get(key) == cbb.getSelectionModel().getSelectedItem()) {
        maLSP = key;
        return maLSP;
      }
    }
    System.out.println(maLSP);
    return maLSP;
  }

  // hien thi thong bao
  private Optional<ButtonType> setAlert(Alert.AlertType alertType, String title, String message) {
    alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText("");
    alert.setContentText(message);
    return alert.showAndWait();
  }

  public void addSP() {
    if (tenSP.getText().isEmpty()
        || donGia.getText().isEmpty()
        || loaiSP.getSelectionModel().getSelectedItem() == null
        || trangThai.getSelectionModel().getSelectedItem() == null
    ) {
      setAlert(Alert.AlertType.ERROR, "Lỗi", "Hãy điền đầy đủ thông tin sản phẩm!");
    } else {
      String maLoai = getMaLSP(loaiSP);
      System.out.println(maLoai);
      int isSell;
      if (trangThai.getSelectionModel().getSelectedItem().equals("Đang bán")) {
        isSell = 1;
      } else {
        isSell = 0;
      }

      // Đường dẫn ảnh
      String path = Current_data.path;
      if (path == null) {
        setAlert(Alert.AlertType.ERROR, "Lỗi", "Đường dẫn ảnh không hợp lệ!");
        return; // Thoát khỏi phương thức nếu không có ảnh
      }
      path = path.replace("\\", "\\\\");

      // Câu lệnh INSERT mới
      String sqlInsert = "INSERT INTO sanpham (maSP, tenSP, loaiSP, donGia, anhSP, trangThai) " +
          "VALUES (?, ?, ?, ?, ?, ?)";

      conn = DBUtils.openConnection();
      try {
        Optional<ButtonType> optional = setAlert(Alert.AlertType.CONFIRMATION, "Xác nhận",
            "Bạn có chắc muốn thêm mới sản phẩm " + tenSP.getText() + "?");
        if (optional.get().equals(ButtonType.OK)) {
          prepare = conn.prepareStatement(sqlInsert);
          String maSP = setMaSP(loaiSP);
          prepare.setString(1, maSP);
          prepare.setString(2, tenSP.getText());
          prepare.setString(3, maLoai);
          prepare.setInt(4, Integer.parseInt(donGia.getText()));
          prepare.setString(5, path);
          prepare.setInt(6, isSell);
          prepare.executeUpdate();  // Thực thi câu lệnh

          setAlert(Alert.AlertType.INFORMATION, "Thông tin", "Thêm mới sản phẩm thành công!");
          showSPList("SELECT * FROM sanpham");
          reloadSP();
        } else {
          setAlert(Alert.AlertType.INFORMATION, "Thông tin", "Đã hủy thêm sản phẩm!");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        setAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi khi thêm sản phẩm: " + e.getMessage());
      } finally {
        DBUtils.closeConnection(conn);
      }
    }
  }

  public void deleteSP() {
    if (Current_data.id == null || Current_data.id.isEmpty()) {
      setAlert(Alert.AlertType.ERROR, "Lỗi", "Hãy chọn sản phẩm cần xóa!");
      return;
    } else {
      Optional<ButtonType> optional = setAlert(Alert.AlertType.CONFIRMATION, "Xác nhận", "Bạn muốn xóa sản phẩm này?");
      if (optional.get().equals(ButtonType.OK)) {
        try {
          String sqlDelete = "DELETE FROM `sanpham` WHERE `maSP`='" + Current_data.id + "'";
          conn = DBUtils.openConnection();
          prepare = conn.prepareStatement(sqlDelete);
          prepare.executeUpdate();
          setAlert(Alert.AlertType.INFORMATION, "Thông tin", "Đã xóa sản phẩm này!");
          DBUtils.closeConnection(conn);
          showSPList("SELECT * FROM sanpham");
          reloadSP();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      } else setAlert(Alert.AlertType.CONFIRMATION, "Thông tin", "Hủy xóa sản phẩm");
    }
  }

  public void updateSP() {
    if (tenSP.getText().isEmpty() ||
        donGia.getText().isEmpty() ||
        Current_data.id == null) {
      setAlert(Alert.AlertType.ERROR, "Lỗi", "Hãy điền đầy đủ thông tin sản phẩm!");
      return; // Thoát nếu thông tin không đầy đủ
    }

    // Lấy thông tin nhập từ người dùng
    String tenSPMoi = tenSP.getText().trim();
    String donGiaMoi = donGia.getText().trim();
    String maLoaiMoi = getMaLSP(loaiSP).trim();
    int isSellMoi = trangThai.getSelectionModel().getSelectedItem().equals("Đang bán") ? 1 : 0;

    // Cập nhật đường dẫn ảnh mới nếu có
    String pathMoi = Current_data.path != null ? Current_data.path.trim() : null;

    conn = DBUtils.openConnection();
    try {
      String sqlSelect = "SELECT tenSP, loaiSP, donGia, anhSP, trangThai FROM sanpham WHERE maSP = ?";
      prepare = conn.prepareStatement(sqlSelect);
      prepare.setString(1, Current_data.id);
      result = prepare.executeQuery();

      if (result.next()) {
        String tenSPOld = result.getString("tenSP").trim();
        String loaiSPOld = result.getString("loaiSP").trim();
        String donGiaOld = result.getString("donGia").trim();
        String anhSPOld = result.getString("anhSP") != null ? result.getString("anhSP").trim() : null;
        int trangThaiOld = result.getInt("trangThai");

        System.out.println("Thông tin cũ: " + tenSPOld + ", " + loaiSPOld + ", " + donGiaOld + ", " + anhSPOld + ", " + trangThaiOld);
        System.out.println("Thông tin mới: " + tenSPMoi + ", " + maLoaiMoi + ", " + donGiaMoi + ", " + pathMoi + ", " + isSellMoi);

        // Kiểm tra nếu thông tin mới giống với thông tin cũ
        boolean isSame = tenSPMoi.equals(tenSPOld) &&
            maLoaiMoi.equals(loaiSPOld) &&
            donGiaMoi.equals(donGiaOld) &&
            ((pathMoi == null && anhSPOld == null) || (pathMoi != null && pathMoi.equals(anhSPOld))) &&
            isSellMoi == trangThaiOld;

        if (isSame) {
          setAlert(Alert.AlertType.INFORMATION, "Thông tin", "Không có thay đổi nào để cập nhật!");
          return;
        }
      }

      // Nếu có thay đổi, thực hiện cập nhật
      String sqlUpdate;
      if (pathMoi != null) {
        sqlUpdate = "UPDATE sanpham SET tenSP = ?, loaiSP = ?, donGia = ?, anhSP = ?, trangThai = ? WHERE maSP = ?";
      } else {
        sqlUpdate = "UPDATE sanpham SET tenSP = ?, loaiSP = ?, donGia = ?, trangThai = ? WHERE maSP = ?";
      }

      prepare = conn.prepareStatement(sqlUpdate);
      prepare.setString(1, tenSPMoi);
      prepare.setString(2, maLoaiMoi);
      prepare.setString(3, donGiaMoi);

      if (pathMoi != null) {
        prepare.setString(4, pathMoi);
        prepare.setInt(5, isSellMoi);
        prepare.setString(6, Current_data.id);
      } else {
        prepare.setInt(4, isSellMoi);
        prepare.setString(5, Current_data.id);
      }

      int rowsAffected = prepare.executeUpdate();
      if (rowsAffected > 0) {
        setAlert(Alert.AlertType.INFORMATION, "Thông tin", "Cập nhật thông tin thành công!");
        showSPList("SELECT * FROM sanpham");
        reloadSP();
      } else {
        setAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật thông tin sản phẩm!");
      }

    } catch (SQLException e) {
      setAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra: " + e.getMessage());
    } finally {
      DBUtils.closeConnection(conn);
    }
  }




}
