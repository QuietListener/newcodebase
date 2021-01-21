package andy.com.algorighm.work2020;

public class Sort {



    public static void main(String [] args){
        int [] nums = {2,1,1,0,7,3};

        new BubbleSort().sort(nums,0,nums.length-1);
        print(nums);

    }


    public static void print(int []nums){
        for(int num : nums){
            System.out.print(num+" ");
        }
    }

    static  void swap(int [] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

     interface AbstractSort {
         public void sort(int nums[] ,int i, int j);
    }


    static class BubbleSort implements  AbstractSort{

        @Override
        public void sort(int[] nums, int i, int j) {
            for(int m = j; m>=i; m--){
                for(int n = i; n <= m-1; n++){
                    if(nums[n] < nums[n+1]){
                        swap(nums, n, n+1);
                    }
                }
            }
        }
    }
}
