module daihocnhatrang.duongthianhhong.blueycoffee {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
  requires java.sql;

  opens daihocnhatrang.duongthianhhong.blueycoffee to javafx.fxml;
    exports daihocnhatrang.duongthianhhong.blueycoffee;
}