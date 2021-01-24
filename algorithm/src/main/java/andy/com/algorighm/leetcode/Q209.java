package andy.com.algorighm.leetcode;

public class Q209 {

    public static  void main(String [] args){
        int a[] = new int[]{2,4,3,1};
        int ret = find(a,7);
        System.out.println(ret);
    }

    public static int   find(int [] nums, int s){

        int start = 0;
        int end = 0;

        int sum =nums[start];
        int minLength = Integer.MAX_VALUE;


        while(start < nums.length && end < nums.length){

            if(sum>=s){
                int curLength = end-start+1;
                if(curLength < minLength){
                    minLength = curLength;
                }
                sum-=nums[start];
                start += 1;

                if(start>end){
                    end = start;
                }

            }else{
                end+=1;
                if(end<nums.length){
                    sum+=nums[end];
                }else{
                    return minLength;
                }
            }
        }

        return minLength;
    }
}
