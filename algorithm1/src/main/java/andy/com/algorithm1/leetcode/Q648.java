package andy.com.algorithm1.leetcode;


import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Q648 {

    private static class TireTree{
        public TireTree(String v){
            this.value = v;
        }

        public String value;
        public boolean wordBound = false;
        public Map<String,TireTree> children = new HashMap<>();

        public TireTree getChildren(String c){
            return children.get(c);
        }

        public TireTree addChild(String c){
            TireTree tmp = children.get(c);
            if(tmp == null){
                tmp = new TireTree(c);
                this.children.put(c,tmp);
            }

            return tmp;
        }
    }

    /**
     * 创建字典树.
     * @param dictionary
     * @return
     */
    public TireTree createTree(List<String> dictionary){
        TireTree root = new TireTree("-1");

        for(String item : dictionary){
            String[] chars = item.split("");

            TireTree tmpRoot = root;
            for(String c: chars){
                TireTree tmp1 = tmpRoot.getChildren(c);
                if(tmp1 == null){
                   tmp1 = tmpRoot.addChild(c);
                }

                tmpRoot = tmp1;
            }

            tmpRoot.wordBound = true;
        }

        return root;
    }


    public String searchTireTree(TireTree root,String word){

        String [] ss = word.split(" ");

        StringBuffer sb = new StringBuffer();
        TireTree node = null;
        for(String s: ss){
             node = root.getChildren(s);
            if(node == null){
                return sb.toString();
            }else{
                sb.append(node.value);
                root = node;
            }
        }

        if(node.wordBound == true){
            return sb.toString();
        }else{
            return "";
        }


    }

    public String replaceWords(List<String> dictionary, String sentence) {
        TireTree tireTree = createTree(dictionary);

        String [] ss = sentence.split("");
        List<String> retList = new ArrayList<>();
        for(String s : ss){
            String s1 = searchTireTree(tireTree, s);

            if(s1.length()>0){
                retList.add(s1);
            }else{
                retList.add(s);
            }
        }

        return retList.stream().collect(Collectors.joining(" "));
    }



    @Test
    public void test(){

        TireTree tireTree = createTree(Arrays.asList("cat","cate","bat"));

        String [] ss = new String [] {"cat121","eee"};

        for(String s : ss){
            String ret = searchTireTree(tireTree,s);
            System.out.println(s+"->"+ret);
        }
    }
}
