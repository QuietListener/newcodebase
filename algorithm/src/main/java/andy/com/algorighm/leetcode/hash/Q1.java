package andy.com.algorighm.leetcode.hash;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 * 你可以按任意顺序返回答案。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 *
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 *
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class Q1 {

    public static void main(String [] args){

       int[] nums = {2,7,11,15};
       int target = 9;

       int [] result = new Q1().twoSum(nums,target);
       System.out.println(String.format("%s,%s",result[0],result[1]));
    }

    /**
     * 解题思路
     * target = a+b;
     * 指定a后，问题变成了,寻找target-a是否存在
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        for(int i = 0; i < nums.length;i++){
            int item = nums[i];
             ArrayList<Integer> indexes = map.get(item);
             if(indexes == null){
                 indexes = new ArrayList<>();
             }

             indexes.add(i);

             map.put(item,indexes);
        }


        int [] result = new int[2];

        for(int i = 0; i < nums.length;i++){
            int item = nums[i];
            int another = target - item;

            List<Integer> indexes = map.get(another);
            if(indexes !=null){
                if(item == another){
                    if(indexes.size() < 2){
                        continue;
                    }

                    else{
                        result[0] = indexes.get(0);
                        result[1] = indexes.get(1);
                       break;
                    }
                }else{
                    result[0] = i;
                    result[1] =  indexes.get(0);
                    break;
                }
            }
        }
        return result;
    }
}
