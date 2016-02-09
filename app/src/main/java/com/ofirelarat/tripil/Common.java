package com.ofirelarat.tripil;

import java.util.Arrays;

public class Common {
    public Hotel[] hotels;

    public static String ArrayToString(String[] arr){
        String temp = Arrays.toString(arr);
        return temp.substring(1, temp.length()-1);
    }
}
