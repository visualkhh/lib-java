package utiltest;

import java.math.BigDecimal;

import com.kdn.util.decimal.DecimalUtil;

public class temp {
    public static void main(String[] args) {
        BigDecimal dm = new BigDecimal("26191741419487.34555");
//        dm = DecimalUtil.decimal(dm, 5);
        System.out.println(dm.toString());
        String a = DecimalUtil.decimalFormat(dm, 10,false);
        System.out.println(a);

    }

}
