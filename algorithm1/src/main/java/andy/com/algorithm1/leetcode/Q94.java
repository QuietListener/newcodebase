package andy.com.algorithm1.leetcode;


import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Q94 {


    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inorderTraversal_1(root,list);
        return list;
    }

    public void inorderTraversal_1(TreeNode root,List<Integer> list) {
        LinkedList<TreeNode> stack = new LinkedList<>();

        while(root!=null){
            stack.push(root);
            root  = root.left;
        }

        while(!stack.isEmpty()){
            TreeNode cur = stack.pop();
            list.add(cur.val);
            if(cur.right!=null){
                TreeNode right = cur.right;
                while(right != null){
                    stack.push(right);
                    right = right.left;
                }
            }
        }

    }


    public void inorderTraversal_(TreeNode root,List<Integer> list) {
         if(root == null){
             return ;
         }

          if(root.left == null && root.right == null){
              list.add(root.val);
              return;
          }

          inorderTraversal_(root.left,list);
          list.add(root.val);
          inorderTraversal_(root.right,list);
    }

    @Test
    public void test(){
        TreeNode root = new TreeNode(5);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(6);

        root.left = left;
        root.right = right;

        List<Integer> ret = new Q94().inorderTraversal(root);

        System.out.println(ret);
    }
}
