package andy.com.jvm.charpter6.classLoadTest;

public class SubClass extends SuperClass {
    static {
        System.out.println("SubClass load");
    }
}
