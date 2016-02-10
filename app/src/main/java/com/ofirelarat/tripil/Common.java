package com.ofirelarat.tripil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Common {
    public static JSONArray concatArray(JSONArray... arrs) throws JSONException {
        JSONArray result = new JSONArray();

        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
        }

        return result;
    }

    public static String ArrayToString(String[] arr){
        String temp = Arrays.toString(arr);
        return temp.substring(1, temp.length()-1);
    }

    public static String GetStringFromMap(Map<String, String> input){
        String ret = "";

        for ( String key : input.keySet() ) {
            ret += key + ": " + input.get(key) + "\r\n";
        }

        return ret;

    }
}
