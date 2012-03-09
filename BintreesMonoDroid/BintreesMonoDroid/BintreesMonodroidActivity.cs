using System;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;

namespace BintreesMonoDroid
{
	[Activity (Label = "BintreesMonoDroid", MainLauncher = true)]
	public class BintreesMonodroidActivity : Activity
	{
    int steps = 15;
    const int minDepth = 4;

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			// Set our view from the "main" layout resource
			SetContentView (Resource.Layout.Main);

			// Get our button from the layout resource,
			// and attach an event to it

			// Button button = FindViewById<Button> (Resource.Id.myButton);

			// button.Click += delegate {
			// 	button.Text = string.Format ("{0} clicks!", count++); };

      Stopwatch watch = new Stopwatch();

      watch.start();
      int maxDepth = Math.Max(minDepth + 2, steps);
      int stretchDepth = maxDepth + 1;

      int check = (TreeNode.bottomUpTree(0,stretchDepth)).itemCheck();
      Console.WriteLine("stretch tree of depth {0}\t check: {1}", stretchDepth, check);

      TreeNode longLivedTree = TreeNode.bottomUpTree(0,maxDepth);

      for (int depth=minDepth; depth<=maxDepth; depth+=2){
        int iterations = 1 << (maxDepth - depth + minDepth);

        check = 0;
        for (int i=1; i<=iterations; i++)
          {
            check += (TreeNode.bottomUpTree(i,depth)).itemCheck();
            check += (TreeNode.bottomUpTree(-i,depth)).itemCheck();
          }

        Console.WriteLine("{0}\t trees of depth {1}\t check: {2}",
                          iterations*2, depth, check);
      }

      Console.WriteLine("long lived tree of depth {0}\t check: {1}",
                        maxDepth, longLivedTree.itemCheck());
      watch.stop();

      Console.WriteLine("Seconds to compute: " + watch.read() + "");
		}

    struct TreeNode
    {
      class Next
      {
        public TreeNode left, right;
      }

      private Next next;
      private int item;

      TreeNode(int item){
        this.item = item;
        this.next = null;
      }

      internal static TreeNode bottomUpTree(int item, int depth){
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
        this.next = new Next ();
        this.next.left = left;
        this.next.right = right;
        this.item = item;
      }

      internal int itemCheck(){
        // if necessary deallocate here
        if (next==null) return item;
        else return item + next.left.itemCheck() - next.right.itemCheck();
      }
    }
	}
}


