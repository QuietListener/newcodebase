package andy.com.algorithm1.leetcode;


import andy.com.algorithm1.utils.AlgorithmUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Q763 {

    static class Pair{
        public int begin = -1;
        public int end = -1;

        @Override
        public String toString() {
            return "Pair{" +
                    "begin=" + begin +
                    ", end=" + end +
                    '}';
        }
    }
    public List<Integer> partitionLabels(String ss) {

        List<Integer> ret = new ArrayList<>();

        Map<String,Pair> map = new LinkedHashMap<>();
        for(int i = 0; i < ss.length(); i++){
            String s = ss.substring(i,i+1);
            Pair p = map.get(s);
            if(p == null){
                p = new Pair();
            }

            if(p.begin < 0 ){
                p.begin = i;
            }

            p.end = i;

            map.put(s,p);
        }


        AlgorithmUtils.print(map.values());


        List<int []> pairs = new ArrayList<>();
        for(Map.Entry<String,Pair> entry: map.entrySet()){
            pairs.add(new int[]{entry.getValue().begin,entry.getValue().end});
        }


        int [][] ret111 = new Q56().merge(pairs.toArray(new int[pairs.size()][2]));
        for(int []  r : ret111){
            System.out.println("["+r[0]+","+r[1]+"]");
        }
        return null;
    }

    @Test
    public void test(){
        String str = "ababcbacadefegdehijhklij";

       new Q763().partitionLabels(str);
    }
}
