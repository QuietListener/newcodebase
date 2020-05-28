package andy.com.javaBasic;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {

    @Test
    public void testCalendar() {

        long nowMs = System.currentTimeMillis();
        long span = 24 * 60 * 60 * 1000;
        for (int i = 0; i < 7; i++) {
            Date d = new Date(nowMs + i * span);
            System.out.println(new SimpleDateFormat("yyyyMMdd").format(d));
            Calendar c  = Calendar.getInstance();
            c.setTime(d);
            System.out.println(c.get(Calendar.DAY_OF_WEEK));

            System.out.println("---------");
        }
    }


    @Test
    public void testDate(){
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int dateInt = Integer.parseInt(dateStr);
        System.out.print(dateInt);
    }
}
