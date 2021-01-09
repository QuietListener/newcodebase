package andy.com.algorighm.leetcode.slipWindow;

public class Q11 {
    public int maxArea(int[] height) {
        int ret = 0;
        for(int i = 0; i< height.length-1;i++){
            for(int j = i+1; j < height.length;j++){
                int s = height[i];
                int e = height[j];
                int area = (j-i)*Math.min(s,e);
                if(ret < area){
                    ret = area;
                }
            }
        }

        return ret;
    }

    public int maxArea1(int[] height) {
        int i = 0;
        int j = height.length;
        int ret = 0;

        while(i<j){
            int curArea = (j-i)*Math.min(height[i],height[j]);
            if(ret < curArea){
                ret = curArea;
            }

            if(height[i]>height[j]){
                i++;
            }else{
                j--;
            }
        }

        return ret;
    }
}
