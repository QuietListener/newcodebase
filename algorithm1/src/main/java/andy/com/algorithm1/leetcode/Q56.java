package andy.com.algorithm1.leetcode;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Q56 {

    public int[][] merge_(int[][] intervals) {
        List<int[]> ret = new ArrayList<>();

        TreeMap<Integer,Integer> startMap = new TreeMap<>();


        for(int i = 0; i < intervals.length; i++){
            startMap.put(intervals[i][0],i);
        }



        ArrayList<Integer> merged = new ArrayList<>();
        ArrayList<int []> ret1 = new ArrayList<>();

        for(Map.Entry<Integer,Integer> entry: startMap.entrySet()){
            int index = entry.getValue();

            if(merged.contains(index)){
                continue;
            }

            int[] item = intervals[index];
            int start = item[0];
            int end = item[1];

            Integer key = startMap.floorKey(end);
            if(key != null){
                int mergedOneIndex = startMap.get(key);
                int [] mergedOne = intervals[mergedOneIndex];

                int newEnd = mergedOne[1];
                if(mergedOne[1] <= end){
                    newEnd = end;
                }

                ret1.add(new int []{start,newEnd});
                merged.add(mergedOneIndex);
            }
        }

        return ret1.toArray(new int[ret1.size()][2]);
    }


    public int[][] merge(int[][] intervals) {
        int [][] ret1 = intervals;

         while(true){
             int [][] ret = merge_(ret1);
             if(ret.length == ret1.length){
                 return ret;
             }else{
                 ret1 = ret;
             }
         }
    }


    @Test
    public void test(){
        int[] [] nums = new int [][] {{1,3},{2,6},{8,10},{15,18}};

        int [][] ret = new Q56().merge(nums);
        for(int []  r : ret){
            System.out.println("["+r[0]+","+r[1]+"]");
        }

    }
}

