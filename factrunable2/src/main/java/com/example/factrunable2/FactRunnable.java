package com.example.factrunable2;

public class FactRunnable implements Runnable {

    private int number, result;

    FactRunnable(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int result = 1;
        for (int count = number; count > 1; count--) {
            result = result * count;
        }
    }

    public int getResult() {
        return result;
    }
}
