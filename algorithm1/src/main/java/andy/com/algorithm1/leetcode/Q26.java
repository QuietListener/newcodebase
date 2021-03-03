package andy.com.algorithm1.leetcode;


import org.junit.Test;

public class Q26 {

    /**
     * 双子针
     * @param nums
     */
    public int removeDup(int [] nums){
        int i = 0;
        int j = 0;
        for( j = 0; j < nums.length;j++){
            if(nums[i] != nums[j]){
                i++;
                nums[i] = nums[j];
            }
        }

        return i+1;
    }

    @Test
    public void test(){
        int [] nums = new int[] {1,2,2,3,4,4};
        int ret = new Q26().removeDup(nums);

        for(int i = 0; i < ret; i++){
            System.out.print(" "+nums[i]);
        }
    }
}
