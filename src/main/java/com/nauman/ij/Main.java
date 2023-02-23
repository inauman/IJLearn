package com.nauman.ij;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public void greet(String name){
        System.out.println("Hello " + name + "!");
    }

    public void log(String logMsg){
        //inc002: fixed log message.
        System.out.println(logMsg);
    }

    class Inner {

    }

    Main.Inner mi;
    Main.InnerX mx;
}