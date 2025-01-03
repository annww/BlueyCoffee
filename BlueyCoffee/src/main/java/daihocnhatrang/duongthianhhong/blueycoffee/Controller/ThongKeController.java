package daihocnhatrang.duongthianhhong.blueycoffee.Controller;

import daihocnhatrang.duongthianhhong.blueycoffee.Utils.DBUtils;
import daihocnhatrang.duongthianhhong.blueycoffee.Utils.PriceUtils;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
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
    displayIncomeToday();
    displayTotalOrder();
    displayIncomeTotal();
    displayIncomeChart();
    displayOrderChart();
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


  public void displayTotalOrderToday() {
    LocalDate today = LocalDate.now();
    String sql = "SELECT COUNT(maHD) FROM hoadon WHERE DATE(createdAt) = ?";

    try (Connection conn = DBUtils.openConnection();
         PreparedStatement prepare = conn.prepareStatement(sql)) {

      // Thiết lập tham số cho câu truy vấn
      prepare.setDate(1, java.sql.Date.valueOf(today));

      try (ResultSet result = prepare.executeQuery()) {
        if (result.next()) {
          int totalOrderT = result.getInt(1);  // Sử dụng chỉ số cột thay vì tên cột
          totalOrderToday.setText(String.valueOf(totalOrderT));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace(); // Hoặc ghi log nếu cần
    }
  }

  private void displayIncomeToday() {
    LocalDate today = LocalDate.now();
    String sql = "SELECT SUM(tongTien) FROM hoadon WHERE DATE(createdAt) = ? AND trangThai = 1";

    try (Connection conn = DBUtils.openConnection();
         PreparedStatement prepare = conn.prepareStatement(sql)) {

      // Thiết lập tham số cho câu truy vấn
      prepare.setDate(1, java.sql.Date.valueOf(today));

      try (ResultSet result = prepare.executeQuery()) {
        if (result.next()) {
          int totalIncomeT = result.getInt(1);  // Sử dụng chỉ số cột thay vì tên cột
          incomeToday.setText(PriceUtils.formatPrice(totalIncomeT));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace(); // Hoặc ghi log nếu cần
    }
  }

  private void displayOrderChart(){
    orderChart.getData().clear();
    String sql = "SELECT DATE(`createdAt`), COUNT(`maHD`) FROM hoadon WHERE `trangThai` = 1 GROUP BY DATE(`createdAt`) ORDER BY DATE(`createdAt`)";
    conn = DBUtils.openConnection();
    XYChart.Series chart = new XYChart.Series<>();
    try{
      prepare = conn.prepareStatement(sql);
      result = prepare.executeQuery();
      while (result.next()){
        chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
      }
      orderChart.getData().add(chart);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      DBUtils.closeConnection(conn);
    }
  }

  private void displayIncomeChart(){
    incomeChart.getData().clear();
    String sql = "SELECT DATE(`createdAt`), SUM(`tongTien`) FROM hoadon WHERE `trangThai` = 1 GROUP BY DATE(`createdAt`) ORDER BY DATE(`createdAt`)";
    conn = DBUtils.openConnection();
    XYChart.Series chart = new XYChart.Series<>();
    try{
      prepare = conn.prepareStatement(sql);
      result = prepare.executeQuery();
      while (result.next()){
        chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
      }
      incomeChart.getData().add(chart);
      chart.getNode().setStyle("-fx-stroke: #0000FF; -fx-fill: #0000FF;");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      DBUtils.closeConnection(conn);
    }
  }
  }
