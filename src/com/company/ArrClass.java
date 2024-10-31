package com.company;

public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = new int[dim];
        this.threadNum = threadNum;
        for(int i = 0; i < dim; i++){
            arr[i] = i;
        }
    }

    public long partSum(int startIndex, int finishIndex){
        long sum = 0;
        for(int i = startIndex; i < finishIndex; i++){
            sum += arr[i];
        }
        return sum;
    }

    private long sum = 0;

    synchronized private long getSum() {
        while (getThreadCount()<threadNum){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sum;
    }

    synchronized public void collectSum(long sum){
        this.sum += sum;
    }

    private int threadCount = 0;
    synchronized public void incThreadCount(){
        threadCount++;
        notify();
    }

    private int getThreadCount() {
        return threadCount;
    }

    public long threadSum() {
        ThreadSum[] threadSums = new ThreadSum[threadNum];

        // Создаем 4 потока и разделяем массив на 4 части
        int partSize = dim / threadNum;
        for (int i = 0; i < threadNum; i++) {
            int startIndex = i * partSize;
            int finishIndex = (i == threadNum - 1) ? dim : startIndex + partSize;
            threadSums[i] = new ThreadSum(startIndex, finishIndex, this);
            threadSums[i].start();
        }
        return getSum();
    }
}
