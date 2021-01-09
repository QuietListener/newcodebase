package andy.com.springbase.beanlifetime;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.concurrent.TimeUnit;

public class BeanLiftimeTest {

    public static void main(String [] args){
        AnnotationConfigApplicationContext container = new AnnotationConfigApplicationContext(Config.class);
        TestObject testObject = container.getBean(TestObject.class);
        testObject.hello();;
        container.close();
    }

    public static void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
