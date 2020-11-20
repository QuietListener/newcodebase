package andy.com.algorighm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/sliding-window-maximum/
 */
public class SlipWindow {
    static class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {

            int [] results = new int[nums.length-k+1];
            int max = -1000000;
            for(int i = 0; i< nums.length; i++){


                if(nums[i] >= max){
                    max = nums[i];
                }

                if(i >= k-1 ){
                    results[i-(k-1)] = max;
                }
            }

            return results;
        }
    }

    public final  static  void main(String[] args){
            int [] input =  {1,-1};
            int [] ret = new Solution().maxSlidingWindow(input,1);
            System.out.println(ret);


    }
}
