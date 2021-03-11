package andy.com;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class TestSerialization1 {




    @org.junit.Test
    public void testSerialization(){
        try (//创建一个ObjectInputStream输入流
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.txt"))) {
            TestSerialization.Test t = (TestSerialization.Test) ois.readObject();
            t.test();;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
