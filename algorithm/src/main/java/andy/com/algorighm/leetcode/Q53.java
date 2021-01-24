package andy.com.algorighm.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Q53 {

    public static  void main(String [] args){

    }

    public int findmaxSub(int [] nums){
        if(nums.length == 1){
            return nums[0];
        }

        int sum= 0;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < nums.length; i++){
            if(i == 0 || sum <= 0 ){
                sum = nums[i];

            }else{
                sum += nums[i];
            }

            if(sum > max){
                max = sum;
            }
        }
        return  max;
    }


}
