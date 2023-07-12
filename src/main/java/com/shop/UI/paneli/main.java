package com.shop.UI.paneli;

import com.shop.UI.password.PlainPassHashGenerator;

public class main {
    public static void main(String[] args) {
        PlainPassHashGenerator plainPassHashGenerator=new PlainPassHashGenerator();
        System.out.println(plainPassHashGenerator.generateHashedPassword("ivica123"));
    }
}
