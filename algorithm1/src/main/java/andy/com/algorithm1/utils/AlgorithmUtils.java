package andy.com.algorithm1.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class AlgorithmUtils {
    public static void print(Collection c){
        System.out.println(c.stream().map(t->t.toString()).collect(Collectors.joining(",")));
    }

    public static void print(int [] c){
        for(int i = 0; i < c.length; i++){
            System.out.print(c[i]+" " );
        }
        System.out.println();
    }
}
