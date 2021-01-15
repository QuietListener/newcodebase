package andy.com.algorighm.leetcode.sort;

public class Q215_heap {


    public static  void main(String [] args) {
        int[] nums = {2, 0, 4, 1, 9};
        heapSort(nums);
        print(nums);
    }

    public static void print(int []nums){
        for(int num : nums){
            System.out.print(num+" ");
        }
    }



    public static void adjustDown(int [] nums , int i, int j){
        int lchild = i*2;
        int rchild = lchild+1;

        while(i < j){
            int bigger = -1;
            if(lchild <= j && rchild <= j){
                if(nums[lchild] > nums[rchild]){
                    bigger = lchild;
                }else{
                    bigger = rchild;
                }
            }
            else if(lchild <= j && rchild>j){
                    bigger = lchild;
            }else{
                return;
            }

            if(bigger > 0) {
                if(nums[bigger] <= nums[i]){
                    swap(nums, bigger, i);
                }
            }else{
                return ;
            }

        }
    }


    public static void heapSort(int [] nums){
        int i = nums.length/2+1;

        for(;i >=1; i++){
            adjustDown(nums,i,nums.length-1);
        }
    }
   static  void swap(int [] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
