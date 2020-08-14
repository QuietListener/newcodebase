package andy.com.javaConcurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;


public class CompletionServiceTest {

    @Test
    public void test1() {
        ThreadPoolExecutor pool2 = new ThreadPoolExecutor(
                1,
                2,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.CallerRunsPolicy());

        CompletionService<Long> completionService = new ExecutorCompletionService<>(pool2);

        for (int i = 0; i < 5; i++) {
            long j = i;
            completionService.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {

                    TimeUnit.SECONDS.sleep(2+new Random().nextInt(4));
                    if (j == 2) {
                        throw new Exception("j = 2");
                    }
                    return j * 10;
                }
            });

        }

        for (int i = 0; i < 5; i++) {
            try {
                Future<Long> l = completionService.take();
                System.out.println(i+":"+l.get());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
