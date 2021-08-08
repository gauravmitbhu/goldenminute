package in.mittal.goldenhour;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.bugfender.sdk.Bugfender;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "ALARMRECEIVER";
    int loop=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: called ");
        Bugfender.d(TAG, "onReceive: called");

        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.golden_minute);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d(TAG,"onCompletion: loop:"+loop);
                loop++;
                if(loop<7){
                    long yourmilliseconds = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                    Date resultdate = new Date(yourmilliseconds);
                    Log.d(TAG,"onCompletion: starting mediaplayer at time:"+sdf.format(resultdate));

                    mediaPlayer.start();
                }
                else {
                    Log.d(TAG,"loop ended successfully with loop value:"+loop);
                }
            }
        });

        Log.d(TAG,"now setting next alarm time..");
        GoldenAlarm gd = new GoldenAlarm();
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        long nextAlarmTimeMillis = gd.getAlarmTimeFromGoldenMinuteMillis(gd.getTomorrowGoldenMinuteTimeInMillis());
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextAlarmTimeMillis, alarmIntent);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(nextAlarmTimeMillis);
        Log.d(TAG,"onReceive: next alarm time :"+sdf.format(resultdate));
        Log.d(TAG,"onreceive: alarm set. see you tomorrow.");
    }
}
