package andy.com.proxy.cglib.jvmtransform;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class TestSerialization {

    public static void main(String[] args) {

        A a = new A();
        a.setValue("Test Data");
        A pA = null;
        serializeObject(pA);
    }

    private static void serializeObject(Object obj) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {

            fos = new FileOutputStream("c:\\serObj.ser", false);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (oos != null)
                    oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}