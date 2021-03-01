package andy.com.algorighm.leetcode.slipWindow;

public class Q11 {
    public int maxArea(int[] height) {

    }

    /**
     * https://leetcode-cn.com/problems/container-with-most-water/solution/container-with-most-water-shuang-zhi-zhen-fa-yi-do/
     * @param height
     * @return
     */
    public int maxArea1(int[] height) {
        int i = 0;
        int j = height.length-1;
        int ret = 0;

        while(i<j && j< height.length){
            int curArea = (j-i)*Math.min(height[i],height[j]);
            if(ret < curArea){
                ret = curArea;
            }

            if(height[i]<height[j]){
                i++;
            }else{
                j--;
            }
        }

        return ret;
    }
}
