package  ru.ralnik.wing;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import ru.ralnik.wing.httpPlayer.HttpPlayerFactory;
import ru.ralnik.wing.httpPlayer.PlayerCommands;

/**
 * Created by ralnik on 08.11.17.
 */

public class myTimer extends CountDownTimer {

    private Context context;
    private PlayerCommands vvvv;

    private int timerTrack;
    String TAG = "myDebug";

    public myTimer(Context context, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        //vvvv = new VVVVPlayer(cfg.getHost());
        vvvv = HttpPlayerFactory.getInstance(context).getCommand();
    }

    public void setTimerTrack(int timerTrack){
        this.timerTrack = timerTrack;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.d(TAG,"Осталось секунд: " + millisUntilFinished / 1000);
    }

    @Override
    public void onFinish() {
        //запускаем трек по таймеру
        vvvv.selectById(timerTrack);

    }
}
