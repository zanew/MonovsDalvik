using System;
using System.Threading;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;

namespace ThreadringMonoDroid
{
	[Activity (Label = "ThreadringTest", MainLauncher = true)]
	public class ThreadringMonoDroidActivity : Activity
	{
    public static DateTime startTime = DateTime.UtcNow;

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			// Set our view from the "main" layout resource
			SetContentView (Resource.Layout.Main);

			string[] args = new string[1];
			args[0] = "1000000";

      		ThreadRing.Main(args);
    }
  }

  public class ThreadRing
  {
    internal const int numberOfThreads = 503;
    internal static NamedThread[] threadRing = new NamedThread[503];

    public static void Main(string[] args) {
      for (int i = 0; i < numberOfThreads; i++){
        threadRing[i] = new NamedThread(i+1);
        Console.WriteLine("NamedThread created");
      }

      foreach (NamedThread t in threadRing)
        new Thread(new ThreadStart(t.Run)).Start();
        Console.WriteLine("Thread created.");

      threadRing[0].TakeToken( int.Parse(args[0]) );
    }
  }

  internal class NamedThread
  {
    private int name;
    private AutoResetEvent signal = new AutoResetEvent(false);
    private int token = 0;

    internal NamedThread(int name) {
      this.name = name;
    }

    internal void Run() {
      while (TokenNotDone())
        NextThread().TakeToken(token-1);

      if (token == 0) Console.WriteLine(name);
		  Console.WriteLine("Took: " + (DateTime.Now - ThreadringMonoDroidActivity.startTime));
      NextThread().TakeToken(-1);
    }

    private bool TokenNotDone() {
      signal.WaitOne();
      return token > 0;
    }

    internal NamedThread NextThread() {
      return ThreadRing.threadRing[ name % ThreadRing.numberOfThreads ];
    }

    internal void TakeToken(int x) {
      Console.WriteLine("Token taken {0}", x);
      token = x;
      signal.Set();
    }
  }

}
