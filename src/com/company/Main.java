package com.company;

public class Main {

    public static void main(String[] args) {
        int dim = 10000000;
        int threadNum = 4;
        ArrClass arrClass = new ArrClass(dim, threadNum);
        System.out.println(arrClass.partSum(0,dim));

        System.out.println(arrClass.threadSum());
    }
}
