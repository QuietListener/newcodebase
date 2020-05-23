package andy.com.javaBasic;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class CollectionTests {

    @Test
    public void testMax(){
        int max = Collections.max(Arrays.asList(1,2,-1));
        System.out.println(""+max);
        assert max == 2;
    }
}
