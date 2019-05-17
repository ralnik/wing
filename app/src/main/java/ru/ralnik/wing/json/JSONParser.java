package ru.ralnik.wing.json;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import ru.ralnik.wing.model.Flat;
import ru.ralnik.wing.sqlitedb.AppDatabase;


public class JSONParser {
    final String url;
    final Context context;

    public JSONParser(Context context, String url) {
        this.url = url;
        this.context = context;
        parser();
    }

    private synchronized void parser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                AppDatabase db = AppDatabase.getInstance(context);
                try {
                    //URL url = new URL(current_link);
                    urlConnection =(HttpURLConnection) new URL(url).openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000);
                    urlConnection.connect();
                    reader= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    int responseCode = urlConnection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Gson gson = new Gson();
                        List<Flat> flats = Arrays.asList(gson.fromJson(reader, Flat[].class));

                        for (int i=0;i<flats.size();i++){
                            Log.d("myDebug", flats.get(i).getNom_kv()+"");
                        }

                        for (Flat flat : flats) {
                           // new FlatRepository(context).insert(flat);
                            db.flatDao().insert(flat);
                        }

                        //Log.d("myDebug", "server connected");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if(reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                    Log.d("myDebug","finish");
                }
            }
        }).start();
    }


}
