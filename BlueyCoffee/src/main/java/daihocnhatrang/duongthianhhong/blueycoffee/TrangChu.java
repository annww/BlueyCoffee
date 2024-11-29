package daihocnhatrang.duongthianhhong.blueycoffee;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TrangChu {
  @FXML
  private TableView<?> tableView;
  @FXML
  private TableColumn<?, ?> colId;
  @FXML
  private TableColumn<?, ?> colProductName;
  @FXML
  private TableColumn<?, ?> colPrice;
  @FXML
  private TableColumn<?, ?> colQuantity;

  // Xử lý các hành động từ giao diện
  @FXML
  private void handleLogout() {
    System.out.println("Đăng xuất...");
  }

  @FXML
  private void handleExit() {
    System.exit(0);
  }

  @FXML
  private void handleManageEmployees() {
    System.out.println("Chuyển đến quản lý nhân viên...");
  }

  @FXML
  private void handleManageProducts() {
    System.out.println("Chuyển đến quản lý sản phẩm...");
  }

  @FXML
  private void handleViewReports() {
    System.out.println("Xem báo cáo doanh thu...");
  }

  @FXML
  private void handleHelp() {
    System.out.println("Hiển thị hướng dẫn sử dụng...");
  }

  @FXML
  private void handleAbout() {
    System.out.println("Hiển thị thông tin phần mềm...");
  }

  @FXML
  private void handleAdd() {
    System.out.println("Thêm dữ liệu...");
  }

  @FXML
  private void handleEdit() {
    System.out.println("Sửa dữ liệu...");
  }

  @FXML
  private void handleDelete() {
    System.out.println("Xóa dữ liệu...");
  }
}
