package andy.com.springbase.factory_bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String [] args){
        AnnotationConfigApplicationContext container = new AnnotationConfigApplicationContext(Config.class);

        //返回的是Date, 不是TestObjectFactoryBean
        Object date = container.getBean("date");

        System.out.println(date);
    }
}
