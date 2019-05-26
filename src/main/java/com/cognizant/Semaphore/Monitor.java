package com.cognizant.Semaphore;

public class Monitor {

    boolean lock;
    public void lock(){

        while (lock) {
          System.out.println (Thread.currentThread().getName() + "About to wait");
          try {
              this.wait();
          }catch(InterruptedException e){
              System.out.println(e.getStackTrace());
          }
        }

        lock = !lock;
    }

    public void release(){

        lock = false;
        this.notify();
    }
}
