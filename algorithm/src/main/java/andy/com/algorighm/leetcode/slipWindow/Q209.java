package andy.com.algorighm.leetcode.slipWindow;

public class Q209 {

    public static void main (String [] args) {
        int [] data = {1,2,3,4,5};
        System.out.println(new Q209().minSubArrayLen(15,data));
    }

    public int minSubArrayLen(int s, int[] nums) {


        if(nums.length  == 0){
            return 0;
        }else if(nums.length == 1){
            return nums[0] >= s ? 1:0;
        }

        int idx1 = 0;
        int idx2 = 0;
        int sum = nums[0];
        int minLength = nums.length;
        boolean modified = false;
        for(;(idx1 < nums.length-1 || idx2 < nums.length)&&idx1<=idx2;){
            int span = idx2-idx1+1;
            if(sum>=s ){
                if( span<=minLength){
                    minLength = span;
                    modified = true;
                }

                if(idx1  < nums.length-1){
                    sum -= nums[idx1];
                    idx1+=1;

                }else{
                    idx2+=1;
                    if(idx1 < idx2)
                        sum += nums[idx2];
                }

            }else{
                if(idx2 < nums.length-1){
                    idx2 +=1;
                    if(idx1 < idx2)
                        sum += nums[idx2];
                }else{
                    sum -= nums[idx1];
                    idx1 += 1;
                }

            }

        }

        return modified ? minLength : 0;
    }

}
