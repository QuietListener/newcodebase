package andy.com.algorithm1.leetcode;


import org.junit.Test;

public class Q450 {

    public TreeNode deleteNode(TreeNode root, int key) {

        TreeNode []ret = search(root,key);
        deleteNode_(ret[1],ret[0]);
        return ret[1];
    }


    /**
     * 递归删除
     */
    public void deleteNode_(TreeNode node,TreeNode parent){
        if(node.left == null && node.right == null){
            if(parent.right == node){
                parent.right = null;
            }else{
                parent.left = null;
            }

        }else if(node.right != null){
            TreeNode [] ret = succ(node);
            TreeNode succNode = ret[1];
            TreeNode parent1 = ret[0];

            node.val = succNode.val;
            deleteNode_(succNode,parent1); //递归删除
        }else if(node.left != null){
            TreeNode [] ret = pre(node);
            TreeNode preNode = ret[1];
            TreeNode parent1 = ret[0];
            node.val = preNode.val;
            deleteNode_(preNode,parent1);
        }
    }

    public TreeNode[] search(TreeNode root,int key){

        TreeNode parent = null;
        while(root != null){
            if(root.val == key){
                return new TreeNode[]{parent,root};
            }
            else if (key > root.val){
                parent = root;
                root = root.right;
            }else{
                parent = root;
                root = root.left;
            }
        }

        return new TreeNode[]{parent,null};
    }

    /**
     * 前驱
     * @param node
     * @return
     */
    public TreeNode [] pre(TreeNode node){

        if(node.left == null){
            return null;
        }


        TreeNode parent = null;
        TreeNode leftNode = node.left;
        while(leftNode.right != null){
            parent = leftNode;
            leftNode = leftNode.right;
        }

        return new TreeNode[]{parent,leftNode};
    }

    /**
     * 后继
     * @param node
     * @return
     */
    public TreeNode [] succ(TreeNode node){
        if(node.right == null){
            return null;
        }


        TreeNode parent = null;
        TreeNode rightNode = node.right;
        while(rightNode.left != null){
            parent = rightNode;
            rightNode = rightNode.left;
        }

        return   new TreeNode[]{parent,rightNode};
    }

    @Test
    public void test(){
        String s = "11aba32";
    }
}
