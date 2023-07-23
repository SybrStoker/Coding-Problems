package org.example;

import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Integer num;
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(2);
        nums.add(3);
        nums.add(4);

        Iterator<Integer> iterator = nums.iterator();

        while (iterator.hasNext()){
            num = iterator.next();
            if(num % 2 == 0){
                System.out.println(num + " is even");
            }
        }

    }
}