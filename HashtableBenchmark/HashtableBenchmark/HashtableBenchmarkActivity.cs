using System;
using System.Collections.Generic;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;

namespace HashtableBenchmark
{
	[Activity (Label = "HashtableBenchmark", MainLauncher = true)]
	public class HashtableBenchmarkActivity : Activity
	{
		

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			// Set our view from the "main" layout resource
			SetContentView (Resource.Layout.Main);
			
			int n = 780000;

	        for (int j=0; j<10; ++j) {
			var  startTime = DateTime.UtcNow;
			var hashtable = new Dictionary<int,float> (n);

			for(int i=1; i<=n; ++i) {
				hashtable [i] = 1.0f / i;
			}

			Console.WriteLine("m[100] = " + hashtable [100]);
			Console.WriteLine("Took: " + (DateTime.Now - startTime));
        }
			
			
			
		}
	}
}


