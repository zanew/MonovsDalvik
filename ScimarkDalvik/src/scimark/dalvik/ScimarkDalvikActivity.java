package scimark.dalvik;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import jnt.scimark2.*;


public class ScimarkDalvikActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button startButton = (Button) findViewById(R.id.startBenchmark);
        startButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {

                 double min_time = Constants.RESOLUTION_DEFAULT;

                 int FFT_size       = Constants.FFT_SIZE;
                 int SOR_size       = Constants.SOR_SIZE;
                 int Sparse_size_M  = Constants.SPARSE_SIZE_M;
                 int Sparse_size_nz = Constants.SPARSE_SIZE_nz;
                 int LU_size        = Constants.LU_SIZE;

                 double[] res = new double[6];
                 jnt.scimark2.Random rand = new jnt.scimark2.Random(Constants.RANDOM_SEED);

                 System.out.println("Minimum running time = " + min_time + "seconds");

                 res[1] = kernel.measureFFT(FFT_size, min_time, rand);

                 res[2] = kernel.measureSOR(SOR_size, min_time, rand);

                 res[3] = kernel.measureMonteCarlo(min_time, rand);

                 res[4] = kernel.measureSparseMatmult(Sparse_size_M, Sparse_size_nz, min_time, rand);

                 res[5] = kernel.measureLU(LU_size, min_time, rand);

                 res[0] = (res[1] + res[2] + res[3] + res[4] + res[5]) / 5;

                 System.out.println();
                 System.out.println("SciMark 2.0a");
                 System.out.println();
                 System.out.println("Composite Score: " + res[0]);
                 System.out.print("FFT ("+FFT_size+"): ");

                 if (res[1]==0.0)
                     System.out.println(" ERROR, INVALID NUMERICAL RESULT!");
                 else
                     System.out.println(res[1]);

                 System.out.println("SOR ("+SOR_size+"x"+ SOR_size+"): "
                                    + "  " + res[2]);
                 System.out.println("Monte Carlo : " + res[3]);
                 System.out.println("Sparse matmult (N="+ Sparse_size_M+
                                    ", nz=" + Sparse_size_nz + "): " + res[4]);
                 System.out.print("LU (" + LU_size + "x" + LU_size + "): ");

                 if (res[5]==0.0)
                     System.out.println(" ERROR, INVALID NUMERICAL RESULT!");
                 else
                     System.out.println(res[5]);

                 // print out System info
                 System.out.println();
                 System.out.println("java.vendor: " +
                                    System.getProperty("java.vendor"));
                 System.out.println("java.version: " +
                                    System.getProperty("java.version"));
                 System.out.println("os.arch: " +
                                    System.getProperty("os.arch"));
                 System.out.println("os.name: " +
                                    System.getProperty("os.name"));
                 System.out.println("os.version: " +
                                    System.getProperty("os.version"));





             }
         });

    }
}