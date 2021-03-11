package andy.com.algorithm1.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Q581 {

    public static int solution(int [] nums){

        if(nums.length == 1 || nums.length == 0)
        {
            return 0;
        }
        int i = 0;
        while( i+1 < nums.length && nums[i]<= nums[i+1]){
            i++;
        }

        int j = nums.length-1;
        while(j-1>=0 && nums[j-1]<=nums[j]){
            j--;
        }

        if (i>j){
            return 0;
        }

        int minValueIdx = i;
        int maxValueIdx = i;

        for(int m = i; m <=j; m++){
            if(nums[m] > nums[maxValueIdx]){
                maxValueIdx = m;
            }
            if(nums[m] < nums[minValueIdx]){
                minValueIdx = m;
            }
        }


        while(j<nums.length || i >=0){
            boolean changed = false;
            if(i-1 >= 0 && nums[minValueIdx] < nums[i-1] ){
                i--;
                changed = true;
            }

            if(j+1 < nums.length && nums[j+1] < nums[maxValueIdx] ){
                j++;
                changed = true;
            }

            if(changed == false){
                break;
            }
        }

        return j-i+1;
    }


    @Test
    public void test(){

       System.out.println(solution(new int[]{ 2,3,3,2,4}));
    }
}
