package andy.com.jvm.charpter6.classLoadTest;

public class SuperClass {
    static {
        System.out.println("SuperClass load");
    }
    static public int Value = 1;
}
