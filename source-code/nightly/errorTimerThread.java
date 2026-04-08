
/**
 *
 * @author toasti
 */
public class errorTimerThread implements Runnable{


    @Override
    public void run(){
        for (int i = 0; i < 6; i++){
            try {
                Thread.sleep(1000);
                if (i >= 5){
                    IO.println("Skipping error");
                    break;
                }
            } catch (InterruptedException e) {
            }
        }
        System.exit(0);
    }

}
