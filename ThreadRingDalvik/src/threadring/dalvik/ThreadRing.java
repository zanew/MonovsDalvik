package threadring.dalvik;
import java.util.concurrent.locks.LockSupport;
import java.util.Date;

public class ThreadRing {

  public static class MessageThread extends Thread {
    MessageThread nextThread;
    volatile Integer message;

    public MessageThread(MessageThread nextThread, int name) {
      super(""+name);
      this.nextThread = nextThread;
    }

    public void run() {
      while(true) nextThread.enqueue(dequeue());
    }

    public void enqueue(Integer hopsRemaining) {
      if(hopsRemaining == 0){
        System.out.println(getName());
        System.out.println("exited and stopped.");
        Date endDate = new Date();
        System.out.println("End time: " + endDate.getTime());
        System.exit(0);
      }
      // as only one message populates the ring, it's impossible
      // that queue is not empty
      message = hopsRemaining - 1;
      LockSupport.unpark(this); // work waiting...
    }

    private Integer dequeue(){
      while(message == null){
        LockSupport.park();
      }
      Integer msg = message;
      message = null;
      return msg;
    }
  }
}
