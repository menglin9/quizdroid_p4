package quiz_p4.menglin9.washington.edu.quiz_p4;

/**
 * Created by Menglin on 5/18/15.
 */
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by eric on 5/17/15.
 */
public class DownloadService extends IntentService {
    private DownloadManager dm;
    private long enqueue;
    public static final int ERICS_ALARM = 123;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() { super.onCreate(); }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.i("DownloadService", "entered onHandleIntent()");
        // Hooray! This method is called where the AlarmManager shouldve started the download service and we just received it here!

        // Specify the url you want to download here
        //String url = "http://tednewardsandbox.site44.com/questions.json";
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String url = "";
        sharedPrefs.getString("prefUrl", "");

        Toast.makeText(getApplicationContext(),
                "Download", Toast.LENGTH_LONG).show();

        Log.i("DownloadService", "should be downloading here");
        Log.i("url", "" + url);

        // Star the download
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        enqueue = dm.enqueue(request);

    }

    public static void startOrStopAlarm(Context context, boolean on) {
        Log.i("DownloadService", "startOrStopAlarm on = " + on);

        Intent alarmReceiverIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ERICS_ALARM, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (on) {
            //int refreshInterval = 5 * 60000; // 5 min x 60,000 milliseconds = total ms in 5 min
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            String refreshInterval = "";
            //sharedPrefs.getString("prefUpdateFrequency", "");
            refreshInterval = sharedPrefs.getString("prefUpdateFrequency", "5");


            Log.i("DownloadService", "setting alarm to " + refreshInterval);

            // Start the alarm manager to repeat
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Integer.parseInt(refreshInterval), pendingIntent);
        }
        else {
            manager.cancel(pendingIntent);
            pendingIntent.cancel();

            Log.i("DownloadService", "Stopping alarm");
        }
    }
}
