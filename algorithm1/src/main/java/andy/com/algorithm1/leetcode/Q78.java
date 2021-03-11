package andy.com.algorithm1.leetcode;

import com.sun.xml.internal.stream.StaxXMLInputSource;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Q78 {

    public static List<List<Integer>>  solution(int [] nums){
            Set<HashSet<Integer>>  result = solution_(nums,nums.length-1);
            return result.stream().map(t->new ArrayList<>(t)).collect(Collectors.toList());
    }

    public static Set<HashSet<Integer>>  solution_(int [] nums,int n){


        if(n == 0){
            Set<HashSet<Integer>> items = new HashSet<HashSet<Integer>>();
            items.add(new HashSet<>());

            HashSet<Integer> tmp = new HashSet<>();
            tmp.add(nums[0]);
            items.add(tmp);

            return items;
        }

        Set<HashSet<Integer>> preItems = solution_(nums, n-1); //递归
        Set<HashSet<Integer>> curItems = new HashSet<HashSet<Integer>>();
        for(Set<Integer> items: preItems){
            HashSet<Integer> newItems = new HashSet<>(items);
            items.add(nums[n]);
            curItems.add(newItems);
        }

        preItems.addAll(curItems);

        return preItems;
    }


    public static List<Integer> addItem(List<Integer> items,Integer num){
        if(items.size() == 0){
            items.add(num);
            return items;
        }

        for(int i = 0; i < items.size();i++){
            items.add(i,num);
        }

        return items;
    }
    @Test
    public void test(){

       solution(new int[]{1,2,3,4});
    }
}
