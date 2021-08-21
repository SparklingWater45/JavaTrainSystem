package com.company;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class test {

    public static void main(String[] args) {

        LinkedList<String> ll = new LinkedList();
        ll.add("Hello");
        ll.add("goodbye");

        ListIterator listIter = ll.listIterator();

        while(listIter.hasNext()){
            System.out.println(listIter.next());
        }




        LinkedList<Station> test = new LinkedList();

        Station o = new Station("o");
        Station b = new Station("b");




        test.add(o);
        test.add(b);


        ListIterator testIterator = test.listIterator();


        //go to end and loop back




        while(testIterator.hasNext()){
            //fetch object variable from the
            Station temp = test.get(testIterator.nextIndex());
            System.out.println(temp.getName());
            testIterator.next();
        }
        do{
            Station temp = test.get(testIterator.previousIndex());
            System.out.println(temp.getName());
            testIterator.previous();
        }while(testIterator.hasPrevious());






    }
}
