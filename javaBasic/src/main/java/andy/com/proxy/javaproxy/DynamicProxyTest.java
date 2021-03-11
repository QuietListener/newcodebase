package andy.com.proxy.javaproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理测试
 * 缺点：必须要有interface
 * cglib没有interface这个限制
 */
public class DynamicProxyTest {
    interface IHello {
        void sayHello();
    }

    static class Hello implements IHello{

        public void sayHello() {
            System.out.println("hello");
        }
    }

    static class DynamicProxy implements InvocationHandler{

        Object originObj;

        Object bind(Object originObj ){
            this.originObj = originObj;
            return Proxy.newProxyInstance(originObj.getClass().getClassLoader(),
                    originObj.getClass().getInterfaces(),this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
            return method.invoke(originObj,args);
        }
    }


    public static void main(String [] args){

        /*
          Exception in thread "main" java.lang.ClassCastException: andy.com.jvm.charpter9.$Proxy0 cannot be cast to andy.com.jvm.charpter9.DynamicProxyTest$Hello
          Hello h = (Hello)new DynamicProxy().bind(new Hello());
          h.sayHello();
         */

        IHello h = (IHello)new DynamicProxy().bind(new Hello());
        h.sayHello();
        System.out.println(h.getClass().getName());
    }

}
