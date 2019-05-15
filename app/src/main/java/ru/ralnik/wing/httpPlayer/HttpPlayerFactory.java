package ru.ralnik.wing.httpPlayer;

import android.content.Context;

import ru.ralnik.wing.config.myConfig;

public class HttpPlayerFactory {
    private static PlayerCommands playerCommands = null;
    private static HttpPlayerFactory instance = null;
    private Context context;
    private myConfig cfg;

    public HttpPlayerFactory(Context context){
        this.context = context;
        cfg = new myConfig(context);
    }

    public static synchronized HttpPlayerFactory getInstance(Context context) {
        if (instance == null) {
            instance = new HttpPlayerFactory(context);
        }
        return instance;
    }


    public PlayerCommands getCommand() {
        if (playerCommands == null) {
            //playerCommands = new VLCPlayer(cfg.getHost());
            playerCommands = new VVVVPlayer("192.168.1.100");
        }
        return playerCommands;
    }

}
