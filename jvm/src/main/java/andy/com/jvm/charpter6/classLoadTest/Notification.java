package andy.com.jvm.charpter6.classLoadTest;

/**
 * -XX:+TraceClassLoading
 * 只会加载SuperClass
 */
public class Notification {
    static final public void main(String [] args){
        System.out.println(SubClass.Value);
    }
}
