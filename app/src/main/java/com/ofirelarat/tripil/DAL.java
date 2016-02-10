package com.ofirelarat.tripil;

import android.app.DownloadManager;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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
    private static Map<String, String> Repositories =
            new HashMap<String, String>(){{
                put("Hotels", "1bfc42ff-69cf-4a2b-b7e5-8fb0e8f2d36b");
                put("Zimmers", "b862f15d-b0a1-41c4-8304-1b7483ef6f86");
                put("Guides", "0d3bfdab-ab6b-4d79-91c9-07edf3e9b07e");
                put("Attractions", "07fc9ed5-1ac6-4752-a8c3-90781b3369ad");
            }};
    private final String URL = "http://new.data.gov.il/api/action/datastore_search?resource_id=";
    private String urlRepository;
    private String urlData;

    public DAL(String repository, AsyncListener listener) {
        this(repository, "", listener);
    }

    public DAL(String repository, String data, AsyncListener listener) {
        if (data != "") {
            data = "&q=" + data;
        }

        urlRepository = Repositories.get(repository);
        urlData = data;
        callback = listener;
    }

    public interface AsyncListener {
        public void updateData(JSONArray result, JSONArray records);
    }

    private AsyncListener callback;

    @Override
    protected void onPostExecute(String result) {
        if(result != "") {
            try {
                JSONObject data = (new JSONObject(result)).getJSONObject("result");
                JSONArray fields = data.getJSONArray("fields");
                JSONArray records = data.getJSONArray("records");
                callback.updateData(fields, records);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            urlData = urlData.replaceAll(" ", "%20");
            URL url = new URL(URL + urlRepository + urlData);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            if(responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                con.disconnect();

                return response.toString();
            }

            return "";
        } catch (Exception ex) {
            ex.printStackTrace();

            return "";
        }
    }
}
