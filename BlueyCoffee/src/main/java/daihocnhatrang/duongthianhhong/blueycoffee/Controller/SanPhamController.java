package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import com.mysql.cj.xdevapi.DbDoc;
import daihocnhatrang.duongthianhhong.blueycoffee.Model.Entities.SanPham;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DSUtils;
import javafx.scene.control.Alert;

public class SanPhamController implements Initializable {
  private HashMap<String, String> loaisps = new HashMap<>();
  private ObservableList<SanPham> cardList = FXCollections.observableArrayList();
  private Connection conn;
  private PreparedStatement prepare;
  private Statement statement;
  private ResultSet result;

  public static List<SanPham> cthds = new ArrayList<>();
  private ObservableList<SanPham> cthdList = FXCollections.observableArrayList();
  private String[] loaiTT = new String[]{"Trả tiền mặt", "Chuyển khoản"};
  private String maHD;
  private Alert alert;
  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public ObservableList<SanPham> sanPhamGetDataFromDB(){
    String sqlSelect = "SELECT * FROM sanpham WHERE `trangThai`=1";
    ObservableList<SanPham> listData = FXCollections.observableArrayList();
    conn = DSUtils.openConnection();
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
            loaisps.get(result.getString("loaiSP"))
        );
        listData.add(sp);
      }
    } catch (Exception e){
    }
    DSUtils.closeConnection(conn);
    return listData;
  }

}
