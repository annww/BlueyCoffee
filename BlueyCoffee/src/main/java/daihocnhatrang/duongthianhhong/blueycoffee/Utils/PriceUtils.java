package daihocnhatrang.duongthianhhong.blueycoffee.Utils;

import java.text.DecimalFormat;

public class PriceUtils {
  public static String formatPrice(int price){
    DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
    String format = decimalFormat.format(price);
    return format;
  }
}
