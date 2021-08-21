package com.company;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class test {

    public static void main(String[] args) {


        String a = "John";


        if (a.matches("[A-Za-z]+")) {
            System.out.println("MATCH");
        } else {
            System.out.println("no match");
        }


    }
}
