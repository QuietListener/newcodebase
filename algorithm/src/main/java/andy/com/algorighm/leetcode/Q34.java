package andy.com.algorighm.leetcode;

public class Q34 {

    public static  void main(String [] args){
        int [] nums = new int[]{ 2,2};
        int [] ret = new Q34().findFirstAndLast(nums,1);
        System.out.println(ret[0] +" " + ret[1]);
    }

    public int [] findFirstAndLast(int [] nums,int target){
        int [] ret = new int[2];

        ret[0] = -1;
        ret[1] = -1;

        if(nums.length == 0){
            return ret;
         }

        if(nums.length == 1){
            if(nums[0] == target){
                ret[0] = 0;
                ret[1] = 0;
            }
            return ret;
        }

        if(nums.length == 2){
            if (nums[0] == target && nums[1] != target){
                ret[0] = 0;
                ret[1] = 0;
                return ret;
            }else if (nums[1] == target && nums[0] != target){
                ret[0] = 1;
                ret[1] = 1;
                return ret;
            }else if (nums[0] == target && nums[1] == target){
                ret[0] = 0;
                ret[1] = 1;
                return ret;
            }
        }

        int start = 0;
        int end = nums.length-1;

        int onePosition = -1;
        int prePos= -1;
        while(start < end){

            int pos = (start+end)/2;

            if(pos == prePos){
                break;
            }

            int mid = nums[pos];
            if(target > mid){
                start = pos+1;
            }else if(target < mid){
                end = pos-1;
            }else{
                onePosition = pos;
                break;
            }

            prePos = pos;
        }

        if(start >= 0 && nums[start] == target){
            onePosition = start;
        }

        if( end >= 0 && nums[end] == target){
            onePosition = end;
        }

        if(onePosition >= 0 ){

            for (int i = onePosition; i < nums.length; i++) {
                if (nums[i] == target) {
                    ret[1] = i;
                }
            }

            for (int i = onePosition; i >= 0; i--) {
                if (nums[i] == target) {
                    ret[0] = i;
                }
            }

            return ret;
        }



        return ret;

    }


}
