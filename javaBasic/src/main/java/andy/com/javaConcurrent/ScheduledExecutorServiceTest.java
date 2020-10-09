package andy.com.javaConcurrent;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTest {

    int count = 1;
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);


    /**
     * 测试:如果任务抛出异常， 后面就不会继续执行了
     */
    @Test
    public void test1() throws Exception{
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("count = " + count);

                if(count == 3){
                    int b = 1/0; //抛出异常
                }
                count+=1;
            }
        },2,2, TimeUnit.SECONDS);


        scheduledExecutorService.awaitTermination(30,TimeUnit.SECONDS);
    }
}
