package com.kidd.shopping;

import java.math.BigDecimal;

public class test {
    public static void main(String[] args) {
        String s = "350.000 đ";
        s = s.replaceAll("[.]", "").replaceAll("đ","").trim();
        System.out.println(new BigDecimal(s));
    }
}
