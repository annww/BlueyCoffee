package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.PriceUtils;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;

public class ThongKeController implements Initializable {
  @FXML
  private AreaChart<?, ?> incomeChart;

  @FXML
  private Label incomeToday;

  @FXML
  private Label incomeTotal;

  @FXML
  private BarChart<?, ?> orderChart;

  @FXML
  private Label totalOrder;

  @FXML
  private Label totalOrderToday;

  private Connection conn;
  private Statement statement;
  private PreparedStatement prepare;
  private ResultSet result;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displayTotalOrderToday();
    displayTotalOrder();
    displayIncomeTotal();
  }

  public void displayTotalOrder() {
    String sql = "SELECT COUNT(maHD) AS total FROM hoadon";

    try (Connection conn = DBUtils.openConnection();
         PreparedStatement prepare = conn.prepareStatement(sql);
         ResultSet result = prepare.executeQuery()) {

      if (result.next()) {
        int totalO = result.getInt("total");
        totalOrder.setText(String.valueOf(totalO));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void displayIncomeTotal() {
    String sql = "SELECT SUM(tongTien) AS totalIncome FROM hoadon WHERE trangThai = 1";

    try (Connection conn = DBUtils.openConnection();
         PreparedStatement prepare = conn.prepareStatement(sql);
         ResultSet result = prepare.executeQuery()) {

      if (result.next()) {
        int tongTien = result.getInt("totalIncome");
        incomeTotal.setText(PriceUtils.formatPrice(tongTien));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public void displayTotalOrderToday(){

  }
}
