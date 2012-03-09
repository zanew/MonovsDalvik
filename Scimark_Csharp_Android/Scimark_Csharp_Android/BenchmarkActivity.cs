using System;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;

namespace Scimark_Csharp_Android
{
	[Activity (Label = "Scimark_Csharp_Android", MainLauncher = true)]
	public class BenchmarkActivity : Activity
	{


		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			SetContentView (Resource.Layout.Main);

			var startButton = FindViewById<Button> (Resource.Id.startBenchmark);

			// On button click, run Scimark.
			startButton.Click += (sender, e) => {

        double min_time = Constants.RESOLUTION_DEFAULT;

        int FFT_size       = Constants.FFT_SIZE;
        int SOR_size       = Constants.SOR_SIZE;
        int Sparse_size_M  = Constants.SPARSE_SIZE_M;
        int Sparse_size_nz = Constants.SPARSE_SIZE_nz;
        int LU_size        = Constants.LU_SIZE;

        double[] res = new double[6];
        Scimark_Csharp_Android.Random R = new Scimark_Csharp_Android.Random(Constants.RANDOM_SEED);

        Console.WriteLine("Mininum running time = {0} seconds", min_time);

        res[1] = kernel.measureFFT(FFT_size, min_time, R);

        res[2] = kernel.measureSOR(SOR_size, min_time, R);

        res[3] = kernel.measureMonteCarlo(min_time, R);

        res[4] = kernel.measureSparseMatmult(Sparse_size_M, Sparse_size_nz, min_time, R);

        res[5] = kernel.measureLU(LU_size, min_time, R);

        res[0] = (res[1] + res[2] + res[3] + res[4] + res[5]) / 5;

        Console.WriteLine();
        Console.WriteLine("Composite Score: {0:F2} MFlops",res[0]);
        Console.WriteLine("FFT            : {0} - ({1})", res[1] == 0.0 ? "ERROR, INVALID NUMERICAL RESULT!" : res[1].ToString("F2"), FFT_size);
        Console.WriteLine("SOR            : {1:F2} - ({0}x{0})", SOR_size, res[2]);
        Console.WriteLine("Monte Carlo    :  {0:F2}",res[3]);
        Console.WriteLine("Sparse MatMult : {2:F2} - (N={0}, nz={1})", Sparse_size_M, Sparse_size_nz, res[4]);
        Console.WriteLine("LU             : {1} - ({0}x{0})",LU_size, res[1] == 0.0 ? "ERROR, INVALID NUMERICAL RESULT!" : res[5].ToString("F2"));

			};

		}
	}
}

