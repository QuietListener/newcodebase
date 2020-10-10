package andy.com.jvm.chapter2;
import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出
 */
public class HeapOOM {

    /**
     * -Xmx10m -Xms10m -XX:+HeapDumpOnOutOfMemoryError
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        List<OOMObject> objs = new ArrayList<OOMObject>();
        while (true) {
            objs.add(new OOMObject());
        }
    }

    static public class OOMObject {
    }

}
