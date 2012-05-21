package bintrees.dalvik;

import android.app.Activity;
import android.os.Bundle;

public class BintreesDalvikActivity extends Activity {
    /** Called when the activity is first created. */
    int steps = 15;
    private final static int minDepth = 4;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        BintreesDalvikActivity.program_main(steps,true);
    }
	
    public static void program_main(int steps, boolean isWarm) {

        Stopwatch watch = new Stopwatch();
        watch.start();
        int maxDepth = (minDepth + 2 > steps) ? minDepth + 2 : steps;
        int stretchDepth = maxDepth + 1;

        int check = (TreeNode.bottomUpTree(0,stretchDepth)).itemCheck();
        if (isWarm)
            System.out.println("stretch tree of depth "+stretchDepth+"\t check: " + check);

        TreeNode longLivedTree = TreeNode.bottomUpTree(0,maxDepth);

        for (int depth=minDepth; depth<=maxDepth; depth+=2){
            int iterations = 1 << (maxDepth - depth + minDepth);
            check = 0;

            for (int i=1; i<=iterations; i++){
                check += (TreeNode.bottomUpTree(i,depth)).itemCheck();
                check += (TreeNode.bottomUpTree(-i,depth)).itemCheck();
            }
            if (isWarm)
                System.out.println((iterations*2) + "\t trees of depth " + depth + "\t check: " + check);
        }
        if (isWarm)
            System.out.println("long lived tree of depth " + maxDepth + "\t check: "+ longLivedTree.itemCheck());
        watch.stop();
    }
    
    private static class TreeNode
    {
        private TreeNode left, right;
        private int item;

        TreeNode(int item){
            this.item = item;
        }

        private static TreeNode bottomUpTree(int item, int depth){
            if (depth>0){
                return new TreeNode(
                                    bottomUpTree(2*item-1, depth-1)
                                    , bottomUpTree(2*item, depth-1)
                                    , item
                                    );
            }
            else {
                return new TreeNode(item);
            }
        }

        TreeNode(TreeNode left, TreeNode right, int item){
            this.left = left;
            this.right = right;
            this.item = item;
        }

        private int itemCheck(){
            // if necessary deallocate here
            if (left==null) return item;
            else return item + left.itemCheck() - right.itemCheck();
        }
    }
}