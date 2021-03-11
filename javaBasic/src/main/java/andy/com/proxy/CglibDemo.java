package andy.com.proxy;

import andy.com.TestSerialization;
import andy.com.proxy.javaproxy.DynamicProxyTest;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

public class CglibDemo {
    static class Hello implements Serializable {

        public void sayHello() {
            System.out.println("hello");
        }
    }


    public static Hello getHello(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Hello.class);
        enhancer.setUseCache(false);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {


                Object obj =  methodProxy.invokeSuper(o, objects);

                if(method.getName().equals("sayHello")){
                    System.out.println("MethodInterceptor invoke");
                }

                return obj;
            }
        });

        Hello hello = (Hello)enhancer.create();;
        return hello;
    }

    @Test
    public void test(){

        Hello hello = getHello();
        hello.sayHello();;
        System.out.println(hello.getClass());
    }

    @Test
    public void testSerialization(){
        try (//创建一个ObjectOutputStream输出流
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("cglib.txt"))) {
            //将对象序列化到文件s
            Hello hello = getHello();
            oos.writeObject(hello);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDeSerialization(){

    }
}
