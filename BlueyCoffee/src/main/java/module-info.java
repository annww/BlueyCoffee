module daihocnhatrang.duongthianhhong.blueycoffee {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires mysql.connector.j;

  opens daihocnhatrang.duongthianhhong.blueycoffee to javafx.fxml;
  opens daihocnhatrang.duongthianhhong.blueycoffee.Controller to javafx.fxml;

  exports daihocnhatrang.duongthianhhong.blueycoffee;
  exports daihocnhatrang.duongthianhhong.blueycoffee.Controller;
}
