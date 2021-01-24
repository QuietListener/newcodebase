package andy.com.algorighm.leetcode.slipWindow;

public class Q42 {

    public static void main (String [] args) {
        int [] data = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(new Q42().trap(data));
    }

    public int trap(int[] height) {

        int total = 0;
        int index1 = 0;
        int index2 = 1;

        for(int i = 0; i < height.length;i++){
            if(height[i] > 0){
                index1 = i;
                break;
            }
        }

        while(index1 < height.length-1){
            int cur = height[index1];
            int maxIdx = -1;
            int water = 0;
            int max = 0;
            boolean find = false;
            for(int j = index1+1; j< height.length && find == false;j++){
                if(height[j] > max){
                    maxIdx = j;
                    max = height[j];
                }


                if(height[j] >= cur){
                    find = true;
                }
                else{
                    water += cur-height[j];
                }
            }

            if(find){ //找到一个比cur更大的就直接加上雨水
                total+=water;
            }
            else{ //否则没有找到比cur他更大的就 需要用height[maxIdx]来作为水平面
                if(maxIdx == -1){
                    maxIdx = height.length-1;
                }

                for(int m = index1+1; m < maxIdx; m++){
                   total+= height[maxIdx]-height[m];
                }
            }

            index1 = maxIdx;
        }

        return total;

    }

}
