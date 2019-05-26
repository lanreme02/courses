package com.cognizant.courses;

public class TestThreads {

    public static void main(String... arg) {

        Object ob[] = new Object[]{new Object(), new Object()};
        boolean semaphore[] = new boolean[]{false, false};

        for (int i = 0; i < 2; i++) {
            Thread k = new Thread("" + i) {

                public void run() {
                    try {

                        System.out.println(this.getName() + "started");
                        semaphore[Integer.parseInt(this.getName())] = false;

                        Thread.sleep(10000);

                        System.out.println(this.getName() + "end");
                        semaphore[Integer.parseInt(this.getName())] = true;
                        ob[Integer.parseInt(this.getName())].notify();

                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            };


        }

    }
}
