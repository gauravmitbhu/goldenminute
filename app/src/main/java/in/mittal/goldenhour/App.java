package in.mittal.goldenhour;

import android.app.Application;

import com.bugfender.sdk.Bugfender;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugfender.init(this, "M3l5BaQfKM1ul8KFA3MoEKfL9wbTlema", BuildConfig.DEBUG);
        Bugfender.enableCrashReporting();
        Bugfender.enableUIEventLogging(this);
        Bugfender.enableLogcatLogging();
    }
}