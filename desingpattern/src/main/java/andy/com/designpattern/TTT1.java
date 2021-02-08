package andy.com.designpattern;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TTT1 {
    public static void main(String []args) {
        System.out.println("aa");
    }



//    public List<Integer []> find(int [] nums){
//
//        List<Integer []> ret = new ArrayList<>();
//         Map<Integer,List<Integer>> numsIndexMap = new HashMap<>();
//
//        for(int i = 0; i < nums.length; i++){
//            int num = nums[i];
//             List<Integer> numIndexList = numsIndexMap.get(num);
//             if(numIndexList == null){
//                 numIndexList = new ArrayList<Integer>();
//             }
//
//             numIndexList.add(i);
//             numsIndexMap.put(num,numIndexList);
//        }
//
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
//                if(list == null){
//                    continue;
//                }else{
//                    if(anotherNum == curNum){
//                        if(list.size()>=2){ //找到一个
//                            int num = list.stream().filter(i->i!=j).findFirst();
//                            ret.add(new Integer[]{i,j,num});
//                        }
//                    }
//                    else{
//                          //找到
//                        ret.add(new Integer[]{i,j,list.get(0)});
//                    }
//                }
//            }
//        }
//
//        return ret;
//
//    }



    @Test
    public void test1(){
        System.out.println("aa");
    }
}
