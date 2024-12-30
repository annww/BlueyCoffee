package daihocnhatrang.duongthianhhong.blueycoffee.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtils {
  public static Optional<ButtonType> setAlert(Alert.AlertType alertType, String title, String mess){
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText("");
    alert.setContentText(mess);
    return alert.showAndWait();
  }
}
