package andy.com.algorithm1.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main2 {

    public void solution(){

    }

    public List<List<Integer>>  result(List<Integer> nums, int k){
        List<List<Integer>> cur = new ArrayList<>();
       if(k == 0){
            List<Integer> tmp = new ArrayList<>();
            tmp.add(nums.get(0));
            cur.add(tmp);
            return cur;
       }

       List<List<Integer>> pre =  result(nums,k-1);


       for(List<Integer> preItem: pre){
           List<List<Integer>> tmp = addNewItem(preItem,nums.get(k));
           cur.addAll(tmp);
       }

        return cur;
    }

    public List<List<Integer>> addNewItem(List<Integer> array, int num ){
        List<List<Integer>> ret = new ArrayList<>();

        if(array.size() == 0){

            List<Integer> tmp = new ArrayList<>();
            tmp.add(num);
            ret.add(tmp);
            return ret;

        }

        for(int i = 0; i <=array.size(); i++){
            List<Integer> tmpArray = new ArrayList<>(array);
            tmpArray.add(i,num);
            ret.add(tmpArray);
        }

        return ret;

    }


    @Test
    public void test(){
      List<Integer> nums = new ArrayList<>(Arrays.asList(1,2,3));
      List<List<Integer>> ret = new Main2().result(nums,nums.size()-1);

      for(List<Integer> l: ret){
          System.out.print(l.stream().map(t->t.toString()).collect(Collectors.joining(","))+"  ");
      }


//        nums.add(nums.size(),-1);
//        System.out.println(nums);
    }

}
