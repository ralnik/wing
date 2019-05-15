package ru.ralnik.wing.httpPlayer;


import android.util.Log;
import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;



public class HttpPlayer {

    public String host = "192.168.1.103";
    public int port = 0;
    public String username = null;
    public String password = null;


    public HttpPlayer(String host, int port, String username, String password) {
        this.host = host.split(":")[0];
        //Log.d("myDebug","Чистый IP:"+this.host);
        this.port = port;
        this.username = username;
        this.password = password;
    }



    public HttpPlayer(String host){
        this.host = host.split(":")[0];
    }

    public void setHost(String host) {
        this.host = host;
    }


    public synchronized void executeCommand(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String current_link = (port != 0) ? "http://" + host + ":" + port + "/" + url : "http://" + host + "/" + url;
                Log.d("myDebug", current_link);

                HttpURLConnection urlConnection = null;
                try {
                    //URL url = new URL(current_link);
                    urlConnection = (HttpURLConnection) new URL(current_link).openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-length", "0");
                    urlConnection.setUseCaches(false);
                    urlConnection.setAllowUserInteraction(false);
                    urlConnection.setConnectTimeout(5000);

                    Authenticator.setDefault(new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password.toCharArray());
                        }

                    });
                    urlConnection.connect();

                    int responseCode = urlConnection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d("myDebug", "server connected");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    }
