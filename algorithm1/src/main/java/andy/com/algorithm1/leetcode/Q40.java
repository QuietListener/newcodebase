package andy.com.algorithm1.leetcode;


import org.junit.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Q40 {

    public void find(int[] nums){

    }

    public List<List<Integer>> dfs(int[] nums, int idx, int target){


        List<List<Integer>> ret = new ArrayList<>();
        if(idx == -1){
            ret.add(new ArrayList<>());
            return ret;
        }
        int curNum = nums[idx];
        List<List<Integer>> pre = dfs(nums,idx-1,target);
        List<List<Integer>> cur = new ArrayList<>(pre);

        for(int i = 0; i < pre.size();i++){
            List<Integer> list =  pre.get(i);
            List<Integer> listCur = addNewNum(list,curNum, target);
            if(listCur!=null){
                cur.add(listCur);
            }
        }

        return cur;
    }

    public List<Integer> addNewNum(List<Integer> list, int curNum,int target){
        int sum = list.stream().mapToInt(t->t).sum();
        if(sum+curNum > target){
            return null;
        }else if(sum+curNum == target){
            System.out.println(list.stream().map(t->t+"").collect(Collectors.joining(","))+","+curNum);
            return null;
        }else{
            List<Integer> newList = new ArrayList<>();
            newList.addAll(list);
            newList.add(curNum);
            return newList;
        }
    }


    public void dfs1(int [] nums , int pos, LinkedList<Integer> stack, int sum, int target){

        for(int i = pos; i < nums.length; i++){
            if(sum + nums[i] == target){
                System.out.println(stack.stream().map(t->t+"").collect(Collectors.joining(","))+","+nums[i]);
            }else if(sum + nums[i] < target){
                LinkedList<Integer> s = new LinkedList<>();
                s.addAll(stack);
                s.add(nums[i]);
                if(i>pos){
                    dfs1(nums,i,s,sum+nums[i],target);
                }
            }
        }
    }


    @Test
    public void test(){
      int [] nums = new int[]{10,1,2,7,6,1,5};
//       List<List<Integer>>  ret = dfs(nums,nums.length-1,8);

        dfs1(nums,0,new LinkedList<>(),0,8);
    }
}
