import java.util.HashMap;
import java.util.Map;

public class medianOfIntegerStream {
    private static TreeNode root;
    private static int mode;
    private static int modeElementCount;
    private static Map<Integer, Integer> mp;
    public static void main(String[] args) {

        mp = new HashMap<>(); // for mode calculation
        root = null;
        mode = -1;
        modeElementCount = 0;
        root = addNumber(1, root);
        root = addNumber(3, root);
        root = addNumber(4, root);
        root = addNumber(2, root);
        root = addNumber(0, root);
        root = addNumber(22, root);
        root = addNumber(0, root);

        double median = getMedian(root);

        System.out.println("Median is " + median);
        System.out.println("Mode is " + mode);;
    }

    // adds an integer to the tree
    public static TreeNode addNumber(int num, TreeNode root)
    {
        // for finding our the mode;
        checkMode(num);

        if(root == null)
        {
            root = new TreeNode(num);
            root.left = root.right = null;
            return root;
        }

        if(root.val <= num)
        {
             root.right = addNumber(num, root.right);
        }else root.left =addNumber(num, root.left);

        root.size = (root.left == null ? 0 : root.left.size) +
                (root.right == null ? 0 : root.right.size) + 1;
        return root;
    }

    // checks if the current element's count is more than the
    // already existing elements' count, then update the
    // mode and modeElementCount;
    private static void checkMode(int num) {
        mp.put(num, mp.getOrDefault(num, 0)+1);
        if(mp.get(num) > modeElementCount)
        {
            mode = num;
            modeElementCount = mp.get(num);
        }
    }


    // if the size of the nodes in root is even, the idea is to find the nodes which will have n/2 elements each
    // then return their average.
    // if the size of the root node is odd, we need to find out the node which has N/2 elements with it.
    public static double getMedian(TreeNode root)
    {
        int size = root.size;
        if(size % 2 == 0)
        {
            int leftVal = root.getNodeWithSize(size/2).val;
            int rightVal = root.getNodeWithSize(size/2 - 1).val;
            return ( (leftVal + rightVal) / 2.0);
        } else return root.getNodeWithSize(size/2).val;
    }
}

class TreeNode {
    int size;
    int val;
    TreeNode left, right;

    public TreeNode(int value)
    {
        this.val = value;
        this.size =1;
        this.left =  this.right = null;
    }

    // returns the node which contains count number of nodes within its left and right sub trees;
    public TreeNode getNodeWithSize(int count) {
        TreeNode temp =  this;

        while(true)
        {
            int leftNodes = temp.left == null ? 0 : temp.left.size;
            if(leftNodes == count) return temp;
            if(leftNodes > count) // if leftNode has more elements than asked for, then we go and find the node in the left side
            {
                temp = temp.left;
            } else {
                // if leftNode has lesser number of nodes, then we try to find out
                // node from the right sub-tree which has count-(nodes present in left Node) elements in its subtrees.
                // In this way we can return a node which will have a total of count number
                // of nodes.
                count = count - leftNodes - 1;
                temp = temp.right;
            }
        }
    }
}


