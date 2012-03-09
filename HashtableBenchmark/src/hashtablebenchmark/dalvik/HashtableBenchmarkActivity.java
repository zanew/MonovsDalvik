package hashtablebenchmark.dalvik;

import android.app.Activity;
import android.os.Bundle;
import java.util.HashMap;

public class HashtableBenchmarkActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        int n = 780000;

        for (int j=0; j<10; ++j) {
            long startTime = System.currentTimeMillis();
            HashMap hashtable = new HashMap(n);

            for(int i=1; i<=n; ++i) {
                hashtable.put(i, 1.0f / i);
            }

            System.out.println("m[100] = " + hashtable.get(100));
            long time = System.currentTimeMillis() - startTime;
            System.out.println("Took: " + time / 1e3 + "s");
    }
}
}