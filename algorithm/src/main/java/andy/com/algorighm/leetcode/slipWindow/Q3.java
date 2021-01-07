package andy.com.algorighm.leetcode.slipWindow;

import java.util.ArrayList;
import java.util.List;

public class Q3 {
    public int lengthOfLongestSubstring(String s) {

        List<String> window =  new ArrayList<>();
        int maxLenth = 0;

        for(int i = 0;i < s.length();i+=1){
            String curS = s.substring(i,i+1);
            int index = window.indexOf(curS);
            if(index < 0){
                window.add(curS);
            }else{
                window = window.subList(index,window.size());
                window.add(curS);
            }

            if(maxLenth<window.size()){
                maxLenth = window.size();
            }
        }

        return maxLenth;
    }

}
