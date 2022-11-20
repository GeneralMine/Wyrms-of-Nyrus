package com.vetpetmon.wyrmsofnyrus.synapselib;

public class threading implements Runnable {
    public void run()
    {
        try {
            // check threads
            System.out.println(
                    "Thread " + Thread.currentThread().getId()
                            + " is running");
        }
        catch (Exception e) {
            // Throws a harmless error.
            System.out.println("A thread was not found in the check, or a threading issue was detected.");
        }
    }
    public static void checkThreads() {
            int n = 8; // Number of threads, we're checking for at most 8, which is at LEAST what most Forge MC players will have.
            for (int i = 0; i < n; i++) {
                Thread object
                        = new Thread(new threading());
                object.start();
            }
    }
}
