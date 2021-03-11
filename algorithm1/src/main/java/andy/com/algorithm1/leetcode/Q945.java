package andy.com.algorithm1.leetcode;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Q945 {

    public static int solution(int [] nums){

        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();

        for(int num: nums){
            Integer count = map.get(num);
            if(count == null){
                count = 0;
            }

            count+=1;
            map.put(num,count);
        }


        List<Integer> keys = map.keySet().stream().collect(Collectors.toList());

        int i = 0;
        int result = 0;
        while(i < keys.size()){
            int key = keys.get(i);
            Integer count = map.get(key);

            if(count > 1){
                Integer nextCount = map.get(key+1);
                if(nextCount == null){
                    nextCount = count-1;
                    keys.add(i+1,key+1);
                }else{
                    nextCount+=count-1;
                }

                result+=count-1;
                map.put(key+1,nextCount);
            }

            i++;

        }

        return result;
    }


    @Test
    public void test(){

       //System.out.println(solution(new int[]{ 3,2,1,2,1,7}));
        System.out.println(solution(new int[]{ 1,2,2}));

        List<Integer> ret = new ArrayList<>(Arrays.asList(1,2,3));
        ret.add(1,5);
        System.out.println(ret);
    }
}
