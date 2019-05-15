package ru.ralnik.wing.httpPlayer;

import ru.ralnik.wing.model.Flat;

public interface PlayerCommands {

    void play();
    void pause();
    void stop();
    void selectById(int id);
    void selectBySubId(int id);
    void volume(int vol);
    void volEffect(int vol);
    void volumeOnOff();
    void toggleLoop();
    void repeat();     //repeat or not repeat
    void random();    //random
    void nextTrack();
    void previousTrack();
    void fullscreen();
    void playlistEmpty();
    void deleteById(int id);

    void setFlatInfo(Flat flat);
    void changeHost(String host);
    void setVolumeOnOff(int volumeOnOff);
    void setVolume(int volume);
    void setVolEffect(int volEffect);

}
