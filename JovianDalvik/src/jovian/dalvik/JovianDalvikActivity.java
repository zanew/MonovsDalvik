package jovian.dalvik;

import jovian.dalvik.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.*;

public class JovianDalvikActivity extends Activity {
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button startButton = (Button) findViewById(R.id.startBenchmark);

        // final int n = (int) numSteps.getText();

        startButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Stopwatch timeBench = new Stopwatch();
                    timeBench.start();

                    NBodySystem bodies = new NBodySystem();
                    System.out.printf("%.9f\n", bodies.energy());
                    for (int i=0; i<100000; ++i)
                        bodies.advance(0.01);
                    System.out.printf("%.9f\n", bodies.energy());
                    timeBench.stop();
                    System.out.println("Seconds to compute: " + timeBench.read() + ".");
                }

            });

    }
}