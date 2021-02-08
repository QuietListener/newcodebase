package andy.com.designpattern;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        Long count = scanner.nextLong();
        BigDecimal x = new BigDecimal(0);
        BigDecimal y = new BigDecimal(0);

        if(count <= 0l){
            return;
        }

        for (long i = 0; i < count; i++) {
            long step = scanner.nextLong();
            long direction = i % 4;

            if (direction == 0) { //向右
                x = x.add(new BigDecimal(step));
            } else if (direction == 1) {//向上
                y = y.add(new BigDecimal(step));
            } else if (direction == 2) {//向左
                x = x.add(new BigDecimal(-step));
            } else {//向下
                y = y.add(new BigDecimal(-step));
            }
            System.out.println(x + " " + y);
        }


    }


}
