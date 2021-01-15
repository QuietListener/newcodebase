package andy.com.algorighm.leetcode.sort;

import org.apache.commons.lang3.stream.Streams;

import java.util.Arrays;
import java.util.stream.Collector;

public class Q215 {


    public static  void main(String [] args) {

        int[] nums = {2, 0, 4, 1, 9};
        //int[] nums = {2, 0};
    //   int i = new Q215().partition(nums, 0, nums.length - 1);
        //System.out.println(i);
        new Q215().quickSort(nums,0,nums.length-1);
        int kth = new Q215().findKthLargest(nums,1);
        System.out.println(kth);
    }

    public int findKthLargest(int[] nums, int k) {
        return findKth(nums,0,nums.length-1,k-1);
    }


    public static void print(int []nums){
        for(int num : nums){
            System.out.print(num+" ");
        }
    }


    int  findKth(int [] nums, int start, int end,int k){

        if(start>=end){
            return nums[start];
        }

        int middle = partition(nums, start,end);
        if(middle == k){
            return nums[k];
        }else if(middle < k){
            return findKth(nums,middle+1,end,k);
        }else{
            return findKth(nums,start,middle-1,k);
        }
    }

    void quickSort(int [] nums, int start, int end){

        if(start>=end){
            return ;
        }
        int middle = partition(nums, start,end);
        quickSort(nums,start,middle-1);
        quickSort(nums,middle+1,end);
    }

    int partition(int []nums ,int start,int end){

        int i = start+1;
        int j = end;
        int m = start;
        while(i <= j){
            while(i <= j && nums[j]<=nums[m]){ //找到第一个比她小的
                j--;
            }

            if(j >= m){
                swap(nums,j,m);
                m = j;
            }


            while(i<=j&&nums[i]>=nums[m]){
                i++;
            }

            if(i <= m){
                swap(nums,m,i);
                m = i;
            }
        }



        return m;
    }

    void swap(int [] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


}
