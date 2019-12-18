package com.kidd.shopping;

import java.math.BigDecimal;
import java.util.Random;

public class test {
    public static void main(String[] args) throws InterruptedException {
        int i = 5;
        Random random = new Random();
        while (true){
            System.out.println(random.nextInt(i));
            Thread.sleep(1000);
        }
    }
}
