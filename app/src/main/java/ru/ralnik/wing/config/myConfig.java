package ru.ralnik.wing.config;

import android.content.Context;
import android.content.SharedPreferences;

import static java.lang.Integer.parseInt;


/**
 * Created by ralnik on 08.11.17.
 */

public class myConfig {
    private String APP_PREF_FILE = "myconfig";
    private String APP_PREF_IP = "IPadress";
    private String APP_PREF_PASS = "Password";
    private String APP_PREF_TIMER = "Timer";
    private String APP_PREF_DISABLE_TIMER = "DisableTimer";
    private String APP_PREF_REPEAT = "Repeat";
    private String APP_PREF_DELAY_STATUS = "DelayStatus";
    private String APP_PREF_VOLUME_PROGRESS = "Volume";
    private String APP_PREF_EFFECT_PROGRESS = "Effect";

    private String APP_PREF_MINSQUARE = "minSquare";
    private String APP_PREF_MAXSQUARE = "maxSquare";
    private String APP_PREF_MINCOST = "minCost";
    private String APP_PREF_MAXCOST = "maxCost";
    private String APP_PREF_MINFLOOR = "minFloor";
    private String APP_PREF_MAXFLOOR = "maxFloor";
    private String APP_PREF_MINBUDGET = "minBudget";
    private String APP_PREF_MAXBUDGET = "maxBudget";

    private String APP_PREF_EMAIL = "email";

    public String getEmail() {
        if(mSettings.contains(APP_PREF_EMAIL)){
            return mSettings.getString(APP_PREF_EMAIL, "ralnik@mail.ru");
        }
        return "";
    }

    public void setEmail(String email) {
        editor.putString(APP_PREF_EMAIL,email);
        editor.apply();
    }

    private SharedPreferences mSettings;
    SharedPreferences.Editor editor;

    public myConfig(Context context){
        mSettings = context.getSharedPreferences(APP_PREF_FILE, Context.MODE_PRIVATE);
        editor = mSettings.edit();
    }

    public void setMinSquare(float min){
        editor.putFloat(APP_PREF_MINSQUARE,min);
        editor.apply();
    }

    public float getMinSquare(){
        if(mSettings.contains(APP_PREF_MINSQUARE)){
            return mSettings.getFloat(APP_PREF_MINSQUARE, 13.40F);
        }
        return 0;
    }

    public void setMaxSquare(float max){
        editor.putFloat(APP_PREF_MAXSQUARE,max);
        editor.apply();
    }

    public float getMaxSquare(){
        if(mSettings.contains(APP_PREF_MAXSQUARE)){
            return mSettings.getFloat(APP_PREF_MAXSQUARE, 149.77F);
        }
        return 0;
    }

    public void setMinCost(float min){
        editor.putFloat(APP_PREF_MINCOST,min);
        editor.apply();
    }

    public float getMinCost(){
        if(mSettings.contains(APP_PREF_MINCOST)){
            return mSettings.getFloat(APP_PREF_MINCOST,0);
        }
        return 0;
    }

    public void setMinBudget(float min){
        editor.putFloat(APP_PREF_MINBUDGET,min);
        editor.apply();
    }

    public float getMinBudget(){
        if(mSettings.contains(APP_PREF_MINBUDGET)){
            return mSettings.getFloat(APP_PREF_MINBUDGET,0);
        }
        return 0;
    }

    public void setMaxCost(float max){
        editor.putFloat(APP_PREF_MAXCOST,max);
        editor.apply();
    }

    public float getMaxCost(){
        if(mSettings.contains(APP_PREF_MAXCOST)){
            return mSettings.getFloat(APP_PREF_MAXCOST,0);
        }
        return 0;
    }

    public void setMaxBudget(float max){
        editor.putFloat(APP_PREF_MAXBUDGET,max);
        editor.apply();
    }

    public float getMaxBudget(){
        if(mSettings.contains(APP_PREF_MAXBUDGET)){
            return mSettings.getFloat(APP_PREF_MAXBUDGET,0);
        }
        return 0;
    }

    public void setMinFloor(int min){
        editor.putInt(APP_PREF_MINFLOOR,min);
        editor.apply();
    }

    public int getMinFloor(){
        if(mSettings.contains(APP_PREF_MINFLOOR)){
            return mSettings.getInt(APP_PREF_MINFLOOR,1);
        }
        return 0;
    }

    public void setMaxFloor(int min){
        editor.putInt(APP_PREF_MAXFLOOR,min);
        editor.apply();
    }

    public int getMaxFloor(){
        if(mSettings.contains(APP_PREF_MAXFLOOR)){
           return mSettings.getInt(APP_PREF_MAXFLOOR,29);
        }
        return 0;
    }

    public void setHost(String host){
        editor.putString(APP_PREF_IP,host);
        editor.apply();
    }

    public String getHost(){
        if (mSettings.contains(APP_PREF_IP)) {
            //получаем число из настроек
         return mSettings.getString(APP_PREF_IP, "192.168.1.103");
        }
        return "";
    }

    public void setPassword(String password){
        editor.putString(APP_PREF_PASS,password);
        editor.apply();
    }

    public String getPassword(){
        if (mSettings.contains(APP_PREF_PASS)) {
            //получаем число из настроек
            return mSettings.getString(APP_PREF_PASS, "");
        }
        return "";
    }

    public void setTimer(String timer){
        editor.putString(APP_PREF_TIMER,timer);
        editor.apply();
    }
    public int getTimer(){
        if (mSettings.contains(APP_PREF_TIMER)) {
            return  parseInt(mSettings.getString(APP_PREF_TIMER, "5"));
        }
        return 5;
    }

    public void setDisableTimer(boolean disableTimer){
        editor.putBoolean(APP_PREF_DISABLE_TIMER,disableTimer);
        editor.apply();
    }

    public boolean getDisableTimer(){
        if (mSettings.contains(APP_PREF_DISABLE_TIMER)){
            return mSettings.getBoolean(APP_PREF_DISABLE_TIMER,false);
        }
        return false;
    }

    public void setRepeat(String repeat){
        editor.putString(APP_PREF_REPEAT,repeat);
        editor.apply();
    }

    public String getRepeat(){
        if (mSettings.contains(APP_PREF_REPEAT)){
            return mSettings.getString(APP_PREF_REPEAT,"RepeatFull");
        }
        return "RepeatFull";
    }

    public int getDelayStatus() {
        if (mSettings.contains(APP_PREF_DELAY_STATUS)) {
            return  parseInt(mSettings.getString(APP_PREF_DELAY_STATUS, "1000"));
        }
        return 1000;
    }

    public void setDelayStatus(String delayStatus) {
        editor.putString(APP_PREF_DELAY_STATUS,delayStatus);
        editor.apply();
    }

    public void setVolumeProgress(String Volume){
        editor.putString(APP_PREF_VOLUME_PROGRESS,Volume);
        editor.apply();
    }

    public int getVolumeProgress() {
        if (mSettings.contains(APP_PREF_VOLUME_PROGRESS)){
            return parseInt(mSettings.getString(APP_PREF_VOLUME_PROGRESS, "1"));
        }
        return 1;
    }

    public void setEffectProgress(String Effect){
        editor.putString(APP_PREF_EFFECT_PROGRESS,Effect);
        editor.apply();
    }

    public int getEffectProgress() {
        if (mSettings.contains(APP_PREF_EFFECT_PROGRESS)){
            return parseInt(mSettings.getString(APP_PREF_EFFECT_PROGRESS, "1"));
        }
        return 1;
    }
}

