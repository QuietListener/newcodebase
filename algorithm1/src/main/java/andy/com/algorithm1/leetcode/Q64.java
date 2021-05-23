package andy.com.algorithm1.leetcode;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q64 {

    private static int currentMin = Integer.MAX_VALUE;
    /**
     * 双子针
     * @param nums
     */
    public int minPathSum(int[][] grid) {

        Stack<int[]> path = new Stack();
        path.push(new int[]{0,0});
        minPathSum_(grid,0,0,grid[0][0],path);

        return currentMin;
    }


    public void minPathSum_(int [][] grid, int i, int j, int sum, Stack<int []> path){
        int w = grid.length;
        int h = grid[0].length;

        System.out.println("i="+i+" j="+j+" sum="+sum);
        printPath(path);

        if(i == w-1 && j== h-1){

            if(sum < currentMin){
                currentMin = sum;
            }
            return;
        }

        for(int k = 0; k < 2; k++){
            if(k == 0){//向右边
                int tmpj = j+1;
                if(tmpj>h-1){
                   continue;
                }
                int tmpsum = sum+grid[i][tmpj];
                path.push(new int[]{i,tmpj});
                minPathSum_(grid,i,tmpj, tmpsum,path);
                path.pop();
            }else if(k == 1){//向下
                int tmpi = i+1;
                if(tmpi > w-1){
                    continue;
                }

                int tmpsum = sum+ grid[tmpi][j];
                path.push(new int[]{tmpi,j});
                minPathSum_(grid,tmpi,j, tmpsum,path);
                path.pop();
            }
        }

    }

    public static void printPath(List<int []> path){
        for(int [] item: path){
            System.out.print("("+item[0]+","+item[1]+")");
        }
        System.out.println("");
    }


    @Test
    public void test(){
        int [][] nums = new int[][] {{1,3,1},{1,5,1},{4,2,1}};
        int ret = new Q64().minPathSum(nums);

        System.out.println("-------------");
        System.out.println(ret);
    }

    @Test
    public void test1(){

    }
}
