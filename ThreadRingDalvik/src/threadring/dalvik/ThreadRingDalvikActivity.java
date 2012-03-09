package threadring.dalvik;

import android.app.Activity;
import android.os.Bundle;
import java.util.concurrent.locks.LockSupport;
import java.util.Date;

import threadring.dalvik.ThreadRing.MessageThread;

public class ThreadRingDalvikActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Date startDate = new Date();
        System.out.println("Start time: " + startDate.getTime());

        Stopwatch watch = new Stopwatch();
        watch.start();
        final int THREAD_COUNT = 503;

        int hopCount = 5000000;

        MessageThread first = null;
        MessageThread last = null;
        for (int i = THREAD_COUNT; i >= 1 ; i--) {
            first = new MessageThread(first, i);
            if(i == THREAD_COUNT) last = first;
        }
        // close the ring:
        last.nextThread = first;
        System.out.println("Thread ring created.");
        // start all Threads
        MessageThread t = first;
        do{
            t.start();
            System.out.println("Thread started.");
            t = t.nextThread;
        }while(t != first);
        // inject message
        first.enqueue(hopCount);
        watch.stop();
            System.out.println("Seconds to compute: " + watch.read() + "");
        try {
            first.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Derp.");
        } // wait for System.exit
    }
}

