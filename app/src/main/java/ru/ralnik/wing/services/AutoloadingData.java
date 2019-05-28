package ru.ralnik.wing.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import ru.ralnik.wing.json.JSONParser;

public class AutoloadingData extends Service {

    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    public AutoloadingData() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();


        //1 сек = 1000 мл.сек
        //1 мин = 60000 мл.сек
        //60 мин = 3600000 мл.сек
        //60мин*24ч = 86400000 мл.сек
        // delay 1000ms, repeat in 86400000ms - или раз в сутки
        mTimer.schedule(mMyTimerTask, 1000, 86400000);
        //mTimer.schedule(mMyTimerTask, 1000, 60000);
        Log.d("myDebug","Создан сервис");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        Log.d("myDebug","Сервис уничтожен");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Log.d("myDebug","запуск задания по таймеру");
            new JSONParser(getApplicationContext(),"http://feeds.l-invest.ru/realred/");
        }
    }
}
