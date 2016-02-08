package com.ofirelarat.tripil;

import android.app.DownloadManager;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.Exception;import java.lang.String;import java.lang.StringBuffer;import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class DAL extends AsyncTask<String, Void, String>{
    private static DAL instance = null;
    private static Map<String, String> Repositories;
    private static String URL = "new.data.gov/api/action/datastore_search?resource_id=";

    protected DAL() {
        // Exists only to defeat instantiation.
    }

    public static DAL getInstance() {
        if(instance == null) {
            instance = new DAL();
            Repositories = new HashMap<String, String>(){{
                put("Hotels", "1bfc42ff-69cf-4a2b-b7e5-8fb0e8f2d36b");
                put("Zimmers", "b862f15d-b0a1-41c4-8304-1b7483ef6f86");
                put("Guides", "0d3bfdab-ab6b-4d79-91c9-07edf3e9b07e");
                put("Attractions", "07fc9ed5-1ac6-4752-a8c3-90781b3369ad");
            }};
        }

        return instance;
    }

    public static JSONArray Request(String repository) {
        return Request(repository, "");
    }

    public static JSONArray Request(String repository, String data) {
        try {
            String temp = "";

            if (data != ""){
                data = "&q=" + data;
            }

            String response = String.valueOf(DAL.getInstance().execute(URL + Repositories.get(repository) + data));
            JSONArray arr = ((new JSONObject(response)).getJSONObject("result")).getJSONArray("products");

            switch (repository){
                case "Hotels":
                    temp = "Zimmers";
                    break;
                case "Zimmers":
                    temp = "Hotels";
                    break;
                default:
                    return arr;
            }

            response = String.valueOf(DAL.getInstance().execute(URL + Repositories.get(temp) + data));

            return concatArray(arr, ((new JSONObject(response)).getJSONObject("result")).getJSONArray("products"));
        } catch (JSONException e) {
            return null;
        }
    }

    protected String doInBackground(String... params) {
        try {
            final URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private static JSONArray concatArray(JSONArray... arrs) throws JSONException {
        JSONArray result = new JSONArray();

        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
        }

        return result;
    }
}