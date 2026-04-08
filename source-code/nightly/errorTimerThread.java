/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author toasti
 */
public class errorTimerThread implements Runnable{


    @Override
    public void run(){
        for (int i = 0; i < 5; i++){
            try {
                Thread.sleep(1000);
                if (i <= 5){
                    IO.println("Skipping error");
                }
            } catch (InterruptedException e) {
            }
        }
        System.exit(0);
    }

}
