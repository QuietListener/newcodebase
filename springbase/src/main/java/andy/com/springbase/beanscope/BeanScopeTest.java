package andy.com.springbase.beanscope;


import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BeanScopeTest {

    public static void main(String [] args){

        int count = 3;
        CountDownLatch countDownLatch = new CountDownLatch(count+1);
        AnnotationConfigApplicationContext container = new AnnotationConfigApplicationContext(Config.class);

        for(int i = 0; i < count; i++){
            Thread t = new Thread(){
                @Override
                public void run(){

                    for(int j = 0; j < 2; j++){
                        BeanScopeTest.sleep(1);
                        System.out.println(this.getName()+" get Object hashcode = "+container.getBean(TestObject.class).hashCode());
                    }
                    countDownLatch.countDown();
                }
            };

            t.setName("t"+i);
            t.start();

        }

        countDownLatch.countDown();
    }

    public static void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
