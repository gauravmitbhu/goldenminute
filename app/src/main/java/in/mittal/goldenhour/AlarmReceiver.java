package in.mittal.goldenhour;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.bugfender.sdk.Bugfender;

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
                loop++;
                if(loop<7){
                    mediaPlayer.start();
                }
                else {
                    GoldenAlarm gd = new GoldenAlarm();
                    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent i = new Intent(context, AlarmReceiver.class);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, i,
                            PendingIntent.FLAG_CANCEL_CURRENT);
                    alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, gd.getTomorrowAlarmTimeInMillis()
                            + 5 * 1000, alarmIntent);
                }
            }
        });
    }
}
