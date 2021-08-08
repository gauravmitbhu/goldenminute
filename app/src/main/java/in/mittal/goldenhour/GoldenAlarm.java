package in.mittal.goldenhour;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GoldenAlarm {

    private String TAG = "GoldenAlarm:";

    public GoldenAlarm(){}

    public long getNextGoldenMinuteTimeInMillis(){
        long nextAlarmTimeMillis = getTodayGoldenMinuteTimeInMillis();

        if(nextAlarmTimeMillis< System.currentTimeMillis()){
            nextAlarmTimeMillis = getTomorrowGoldenMinuteTimeInMillis();
        }

        return nextAlarmTimeMillis;
    }

    private long getTodayGoldenMinuteTimeInMillis(){
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

    public long getTomorrowGoldenMinuteTimeInMillis(){
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

    public String getNextGoldenMinuteTimeInText() {
        long millis = getNextGoldenMinuteTimeInMillis();
        String dateFormat = "hh:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(millis);
    }

    public long getAlarmTimeFromGoldenMinuteMillis(long goldenMinuteMillis){
        long alarmMillis;

        alarmMillis = goldenMinuteMillis;
        alarmMillis = alarmMillis - 60*1000;

        return alarmMillis;
    }
}
