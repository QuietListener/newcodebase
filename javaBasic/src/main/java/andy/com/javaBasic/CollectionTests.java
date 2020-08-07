package andy.com.javaBasic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionTests {

    @Test
    public void testMax(){
        int max = Collections.max(Arrays.asList(1,2,-1));
        System.out.println(""+max);
        assert max == 2;
    }

    @Test
    public void testRetain(){
        List<Long> l1 = new ArrayList<>(Arrays.asList(1l,2l));
        List<Long> l2 =  new ArrayList<>(Arrays.asList(1l,3l));
        System.out.println(l1.retainAll(l2));
        System.out.println(l1);

    }
}
