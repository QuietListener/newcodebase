package andy.com.proxy.cglib.jvmtransform;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class TestDeSerialization {

    public static void main(String[] args) {

        A pA = (A)deSerializeObject();
        pA.getValue(); //Should return "Test Data"
    }

    private static Object deSerializeObject() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object returnObject = null;
        try {

            fis = new FileInputStream("c:\\serObj.ser");
            ois = new ObjectInputStream(fis);
            returnObject = ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ois != null)
                    ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnObject;

    }

}