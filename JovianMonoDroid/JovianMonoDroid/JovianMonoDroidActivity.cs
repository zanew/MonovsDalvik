
using System;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using Android.Util;

namespace JovianMonoDroid
{
	[Activity (Label = "JovianMonoDroid", MainLauncher = true)]
	public class JovianMonodroidActivity : Activity
	{
		int count = 1;

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			// Set our view from the "main" layout resource
			SetContentView (Resource.Layout.Main);

			// Get our button from the layout resource,
			// and attach an event to it
			Button button = FindViewById<Button> (Resource.Id.myButton);
      button.Text = "Start Jovain Simulation";

			button.Click += delegate {
        Stopwatch timeBench = new Stopwatch();
        timeBench.start();

				NBodySystem bodies = new NBodySystem();
        Console.WriteLine("{0:f9}", bodies.Energy());
        for (int i = 0; i < 100000; i++)
          bodies.Advance(0.01);
        Console.WriteLine("{0:f9}", bodies.Energy());
        timeBench.stop();
        Console.WriteLine("Seconds to compute: " + timeBench.read() + "");
      };
		}
	}
}

