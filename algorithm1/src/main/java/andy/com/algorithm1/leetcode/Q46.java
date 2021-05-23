package andy.com.algorithm1.leetcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全排列
 */
public class Q46 {

    public static void main(String [] args){
        int [] nums = new int [] {2,3,1};
        List<List<Integer>> ret =  new Q46().permute(nums);
        System.out.println(ret);
    }

    public List<List<Integer>> permute(int[] nums) {
       List<Integer> list = new ArrayList<>();
       list.add(null);
       list.add(null);
       list.add(null);
        List<List<Integer>> ret = new ArrayList<>();
       fllsort(ret, list,nums,0);
       return ret;
    }

    public void fllsort( List<List<Integer>> ret,List<Integer> list,int [] subArray,int currentLength ){



        if(list.size() == subArray.length && list.get(subArray.length-1) != null){
            ret.add(new ArrayList<>(list));
            print(list);
            return ;
        }

        for(int i = 0; i < subArray.length; i++){
            if(list.indexOf(subArray[i]) >= 0){
                continue;
            }
            System.out.println("currentLength:"+currentLength);
            list.set(currentLength,subArray[i]);
            this.fllsort(ret,list,subArray,currentLength+1);
            list.set(currentLength,null);
        }

    }

    public static void print(Collection collection){
        System.out.println(collection.stream().map(t->t.toString()).collect(Collectors.joining(",")));
    }


    public List<List<Integer>> permute1(int [] nums, int i){
        int currNum = nums[i];
        List<List<Integer>> newArray = new ArrayList<>();

        if(i == 0){
            List<Integer> first = new ArrayList<>();
            first.add(currNum);
            newArray.add(first);
            return newArray;
        }

        List<List<Integer>> ret = permute1(nums,i-1);
        for(List<Integer> p: ret){
            //加入新元素
        }

        return newArray;

    }
}
