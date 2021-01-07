package andy.com.algorighm.leetcode.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
 *
 *  
 *
 * 示例：
 *
 * s = "leetcode"
 * 返回 0
 *
 * s = "loveleetcode"
 * 返回 2
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/first-unique-character-in-a-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Q387 {
    public static void main(String [] args){

        int result = new Q387().firstUniqChar("loveleetcode");
        System.out.println(String.format("%s",result));
    }

    public int firstUniqChar(String s) {
        Map<String,Integer> map = new HashMap<>();

        for(int i = 0;i < s.length();i++){
            String ss = s.substring(i,i+1);
            Integer count = map.get(ss);
            if(count == null){
                count = 0;
            }

            count+=1;
            map.put(ss,count);
        }

        for(int i = 0; i < s.length();i++){
            String ss = s.substring(i,i+1);
            if(map.get(ss)==1){
                return i;
            }
        }

        return -1;
    }
}
