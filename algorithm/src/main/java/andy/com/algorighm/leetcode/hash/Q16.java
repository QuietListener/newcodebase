package andy.com.algorighm.leetcode.hash;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Q16 {

    public static  void main(String [] args){
        TreeMap<Integer,String> map = new TreeMap<>();
        map.put(2,"a");
        map.put(3,"b");
        map.put(1,"c");
        map.put(-1,"d");

        int ceiling = map.ceilingKey(0);
        int floor = map.floorKey(0);

        System.out.println("c = "+ceiling +"  f = " +floor);

        System.out.println(map.keySet().stream().map(t->t.toString()).collect(Collectors.joining(",")));
    }

//    public List<Integer []> find(int [] nums){
//
//        List<Integer []> ret = new ArrayList<>();
//        TreeMap<Integer,List<Integer>> numsIndexMap = new TreeMap<>();
//
//        for(int i = 0; i < nums.length; i++){
//            int num = nums[i];
//            List<Integer> numIndexList = numsIndexMap.get(num);
//            if(numIndexList == null){
//                numIndexList = new ArrayList<Integer>();
//            }
//
//            numIndexList.add(i);
//            numsIndexMap.put(num,numIndexList);
//        }
//
//
//        int [] cur_result = new int[3];
//        int cur_num = Integer.MIN_VALUE;
//        for(int i = 0; i < nums.length; i++){
//            int result = nums[i];
//
//            for(int j = 0; j < nums.length; j++){
//                if(j == i){
//                    continue;
//                }
//
//                int curNum = nums[j];
//                int anotherNum = -result - curNum;
//
//                List<Integer> list = numsIndexMap.get(anotherNum);
//
//
//                if(list == null){
//                    int ceiling = numsIndexMap.ceilingKey(anotherNum);
//                    int floor = numsIndexMap.floorKey(anotherNum);
//
//                    if(Math.abs(anotherNum - ceiling) < Math.abs(floor-anotherNum)){
//                        if(Math.abs(anotherNum-ceiling) < cur_num){
//                            cur_result = new int[]{nums[i],nums[j],ceiling};
//                            cur_num = Math.abs(anotherNum - ceiling);
//                        }
//
//                    }else{
//
//                        if(Math.abs(anotherNum-ceiling) < cur_num){
//                            cur_result = new int[]{nums[i],nums[j],ceiling};
//                            cur_num = Math.abs(anotherNum - ceiling);
//                        }
//                        cur_result = new int[]{nums[i],nums[j],floor};
//
//                    }
//                }else{
//                    if(anotherNum == curNum){
//                        if(list.size()>=2){ //找到一个
//                            int num = list.stream().filter(i->i!=j).findFirst();
//                            ret.add(new Integer[]{i,j,num});//0直接返回
//                        }
//                        else{ //逻辑 不等于0;
//
//                        }
//                    }
//                    else{
//                        //找到
//                        ret.add(new Integer[]{i,j,list.get(0)}); //0直接返回
//                    }
//                }
//            }
//        }
//
//        return ret;
//
//    }

}
