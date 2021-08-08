package in.mittal.goldenhour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import com.bugfender.sdk.Bugfender;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivityDelete";
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bugfender.d(TAG, "onCreate: calling updater.");
        AppUpdater appUpdater = new AppUpdater(this).setUpdateFrom(UpdateFrom.GITHUB)
                .setGitHubUserAndRepo("gauravmitbhu", "goldenminute");
        appUpdater.start();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }

        GoldenAlarm gd = new GoldenAlarm();

        String time_string = gd.getNextGoldenMinuteTimeInText();
        TextView alarmTimeTextView = (TextView) findViewById(R.id.alarmTimeTextView);
        alarmTimeTextView.setText(time_string);

        Context context = getApplicationContext();
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);

        long nextAlarmTimeMillis= gd.getAlarmTimeFromGoldenMinuteMillis(gd.getNextGoldenMinuteTimeInMillis());
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextAlarmTimeMillis, alarmIntent);
        //alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5*1000
          //    , alarmIntent);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(nextAlarmTimeMillis);
        Log.d(TAG,"next alarm time :"+sdf.format(resultdate));
        Log.d(TAG,"alarm set. see you tomorrow.");
    }
}