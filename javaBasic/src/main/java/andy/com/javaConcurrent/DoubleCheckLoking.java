package andy.com.javaConcurrent;

public class DoubleCheckLoking {
    //volatile 禁止重排序
    public static volatile DoubleCheckLoking instance;

    public static DoubleCheckLoking getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckLoking.class) {
                if (instance == null) {
                    instance = new DoubleCheckLoking();
                }
            }
        }

        return instance;
    }


    public static DoubleCheckLoking getInstance1() {
        return InstanceHolder.instance;
    }
    private static class InstanceHolder{
        static DoubleCheckLoking instance = new DoubleCheckLoking();
    }
}
