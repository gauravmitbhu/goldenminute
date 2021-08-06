package in.gingermind.goldenhour;

import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GoldenAlarm {

    private String TAG = "GoldenAlarm:";

    public GoldenAlarm(){}

    public long getNextAlarmTimeInMillis(){
        long nextAlarmTimeMillis = getTodayAlarmTimeInMillis();

        if(nextAlarmTimeMillis< System.currentTimeMillis()){
            nextAlarmTimeMillis = getTomorrowAlarmTimeInMillis();
        }

        return nextAlarmTimeMillis;
    }

    private long getTodayAlarmTimeInMillis(){
        Calendar calendar =  Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        Log.d(TAG,"getTodayAlarmTimeInMillis: Today: Day="+day+", Month="+(month+1));

        if(day<=24){
            Log.d(TAG, "getTodayAlarmTimeInMillis: Today: Today's alarm is on:"+day+":"+(month+1));
            calendar.set(Calendar.HOUR_OF_DAY,day);
            calendar.set(Calendar.MINUTE,month+1);
        }
        else {
            Log.d(TAG, "getTodayAlarmTimeInMillis: Today: Today's alarm is on:"+(month+1)+":"+day);
            calendar.set(Calendar.HOUR_OF_DAY,month+1);
            calendar.set(Calendar.MINUTE,day);
        }

        calendar.set(Calendar.SECOND,0);

        return calendar.getTimeInMillis();
    }

    public long getTomorrowAlarmTimeInMillis(){
        Calendar calendar =  Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        Log.d(TAG,"getTomorrowAlarmTimeInMillis: Tom: Day="+day+", Month="+(month+1));

        day=day+1;
        calendar.set(Calendar.DAY_OF_MONTH,day);

        if(day<=24){
            Log.d(TAG, "getTomorrowAlarmTimeInMillis: Tom: Tom alarm is on:"+day+":"+(month+1));
            calendar.set(Calendar.HOUR_OF_DAY,day);
            calendar.set(Calendar.MINUTE,month+1);
        }
        else {
            Log.d(TAG, "getTomorrowAlarmTimeInMillis: Tom: Tom alarm is on:"+(month+1)+":"+day);
            calendar.set(Calendar.HOUR_OF_DAY,month+1);
            calendar.set(Calendar.MINUTE,day);
        }

        calendar.set(Calendar.SECOND,0);

        return calendar.getTimeInMillis();
    }

    public String getNextAlarmTimeInText() {
        long millis = getNextAlarmTimeInMillis();
        String dateFormat = "hh:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(millis);
    }
}
