package in.mittal.goldenhour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import com.bugfender.sdk.Bugfender;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

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

        GoldenAlarm gd = new GoldenAlarm();

        String time_string = gd.getNextAlarmTimeInText();
        TextView alarmTimeTextView = (TextView) findViewById(R.id.alarmTimeTextView);
        alarmTimeTextView.setText(time_string);

        Context context = getApplicationContext();
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, gd.getNextAlarmTimeInMillis()
               + 5 * 1000, alarmIntent);
        //alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5*1000
          //    , alarmIntent);
    }
}