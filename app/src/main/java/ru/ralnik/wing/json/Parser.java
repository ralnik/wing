package ru.ralnik.wing.json;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ru.ralnik.wing.config.myConfig;
import ru.ralnik.wing.model.Flat;
import ru.ralnik.wing.sqlitedb.FlatRepository;


/**
 * Created by ralnik on 10.08.17.
 */

public class Parser  {
    private String url = "";

    private String TAG = "myDebug";
    private Context context;
    private myConfig cfg;
    private ArrayList<Flat> massive;



    public Parser(Context context){
        this.context = context;
        cfg = new myConfig(context);
        massive = new ArrayList<>();


        new getJsonAsyncTask(context).execute();


    }

    public void setUrl(String url){
        this.url = url;
    }





    public class getJsonAsyncTask extends AsyncTask<Void, Void,  Void> {

        private Context context;

        public getJsonAsyncTask(Context context) {
            this.context = context;

        }

        @Override
        protected  Void doInBackground(Void... voids) {
            ArrayList<Flat> massive = new ArrayList<>();
            try{
            HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
            HttpPost httppost = new HttpPost("http://feeds.l-invest.ru/realred/");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuilder jsonBuilder = new StringBuilder();

            for (String line = null; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }

            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(tokener);

            Log.d(TAG, "jsonArray.length(): "+jsonArray.length());

            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.getJSONObject(index);

                Flat flat = new Flat();

                flat.setId(jsonObject.getString("id_flat"));
                flat.setNom_kv(jsonObject.getInt("nom_kv"));
                flat.setCorpus(jsonObject.getInt("corpus"));
                flat.setEtag(jsonObject.getInt("etag"));
                flat.setComnat(jsonObject.getInt("comnat"));
                flat.setPloshad(Float.valueOf(jsonObject.getString("ploshad")));
                flat.setPrice(Float.valueOf(jsonObject.getString("price")));
                flat.setStatus(jsonObject.getInt("status"));
                flat.setPlanirovka(jsonObject.getString("planirovka"));

                Log.d(TAG, "index: "+index+ " "+ flat.getId()+" "+flat.getNom_kv());
                //будет вставленa новая запись, если запись уже есть, то тогда она обновится полностью по всем полям
                new FlatRepository(this.context).insert(flat);



            }


        } catch (FileNotFoundException e) {
            Log.e(TAG, "jsonFile not found");
            Log.e(TAG,e.toString());
        } catch (IOException e) {
            Log.e(TAG, "jsonFile ioerror");

        } catch (JSONException e) {
            Log.e(TAG, " jsonFile error while parsing json");
            Log.e(TAG, " error parsing: "+e.toString());
        }
        finally {

        }

            return  null;
        }


    }

}
