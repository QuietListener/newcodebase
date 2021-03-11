package andy.com.algorithm1.leetcode;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Q215 {

   public  int solution(int[] nums ,int kth){

       //调整为大顶堆
       for(int i = nums.length/2; i>=0; i++){
           adjustDown(nums,i,nums.length-1);
       }

       for(int i = 1; i <= kth; i++){
           swap(nums,0,nums.length-i); //最大的交换到 最后面;
           adjustDown(nums,0,nums.length-i);
       }

       return nums[nums.length-kth];
   }

   public void swap(int[] nums, int i, int j){
       int tmp = nums[i];
       nums[i] = nums[j];
       nums[j] = tmp;
   }

   public void adjustDown(int[] nums, int from,int to){

       int cur = from;
       int leftChild = cur*2+1;
       int rightChild = cur*2+2;

       while(cur < to){

           int maxChild = -1;
           if(rightChild >to && leftChild > to ){
               break;
           }else if(rightChild > to && leftChild <= to){
              maxChild = leftChild;
           }else{
               maxChild  = nums[leftChild] > nums[rightChild] ? leftChild : rightChild;
           }


           if(nums[cur] < nums[maxChild]){
               swap(nums,cur,maxChild);
           }

           cur = maxChild;
           leftChild = cur*2+1;
           rightChild = cur*2+2;
       }
   }

    @Test
    public void test(){

       System.out.println(new Q215().solution(new int[]{2,3,1,4,6},2));
    }
}

