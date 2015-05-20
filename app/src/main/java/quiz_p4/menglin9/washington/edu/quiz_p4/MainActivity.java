package quiz_p4.menglin9.washington.edu.quiz_p4;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    public String[] topics = {"Math", "Physics", "Unix"};
    public int[] icons;
    private ListView quizList;
    private static final int SETTINGS_RESULT = 1;

    private DownloadManager dm;
    private long enqueue;
    int time = 10;
    AlarmManager am;

    PendingIntent alarmIntent = null;
    BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            String url = "";
            url = sharedPrefs.getString("prefUrl", "Please enter URL in preference!");
            //Log.e("hi","hihihi"+url);
            Toast.makeText(MainActivity.this,"" + url,Toast.LENGTH_SHORT).show();

            String refreshInterval = "";
            refreshInterval = sharedPrefs.getString("prefUpdateFrequency", "10");
            time = Integer.parseInt(refreshInterval);
            Log.e("te", "" + time);
            am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 5000, time * 8 * 1000, alarmIntent);

            // This is where we start our DownloadService class! aka tell our IntentService to start the download!
            Intent downloadServiceIntent = new Intent(context, DownloadService.class);
            context.startService(downloadServiceIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Log.i("appa", "QuizApp has been loaded and run!!!!!!!!");

        // Calling async task to get json
        //new GetContacts().execute();

        IntentFilter filter = new IntentFilter("quiz_p4.menglin9.washington.edu.quiz_p4");
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE); // Add more filters here that you want the receiver to listen to
        //registerReceiver(alarmReceiver, new IntentFilter("quiz_p4.menglin9.washington.edu.quiz_p4"));
        registerReceiver(alarmReceiver, filter);
        //AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent();
        i.setAction("quiz_p4.menglin9.washington.edu.quiz_p4");
        alarmIntent = PendingIntent.getBroadcast(this,0,i,0);


        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 5000, time * 8 * 1000, alarmIntent);
        // Register Receiver aka Listen if the DownloadManager from Android OS publishes a "Download has complete"-like broadcast
        //      -note that  DownloadManager is a system service that can be accessed anywhere.

        //IntentFilter filter = new IntentFilter();
        //filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE); // Add more filters here that you want the receiver to listen to
        //registerReceiver(receiver, filter);

        // get your Application singleton this time//////
        QuizApp quizApp = (QuizApp) getApplication();

        if (quizApp.getSuccess()) {
            topics = quizApp.getAllTopics();
            icons = quizApp.getAllIcons();
        }
        //myApp.questions.get("blah blah idk"); // grab your repository from MyApp and get data from it

        quizList = (ListView) findViewById(R.id.listView);
        // ArrayAdapter<String> items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topics);
        //ArrayAdapter<String> items = new ArrayAdapter<String>(this, R.layout.mylist, R.id.Itemname, topics);
        //quizList.setAdapter(items);

        //set the custom adapter for loading the individual image
        quizList.setAdapter(new CustomAdapter(this, topics, icons));

        quizList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("in", "ink");
                Intent next = new Intent(MainActivity.this, Quiz_Content.class);
                next.putExtra("position", position);
                startActivity(next);

            }
        });
    }

    // This is your receiver that you registered in the onCreate that will receive any messages that match a download-complete like broadcast
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);

            Log.i("MyApp BroadcastReceiver", "onReceive of registered download reciever");

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Log.i("MyApp BroadcastReceiver", "download complete!");
                long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                // if the downloadID exists
                if (downloadID != 0) {

                    // Check status
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadID);
                    Cursor c = dm.query(query);
                    if(c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        Log.d("DM Sample","Status Check: "+status);
                        switch(status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                // The download-complete message said the download was "successfu" then run this code
                                ParcelFileDescriptor file;
                                StringBuffer strContent = new StringBuffer("");

                                try {
                                    // Get file from Download Manager (which is a system service as explained in the onCreate)
                                    file = dm.openDownloadedFile(downloadID);
                                    FileInputStream fis = new FileInputStream(file.getFileDescriptor());

                                    // YOUR CODE HERE [convert file to String here]
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                                    StringBuilder out = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        out.append(line);
                                    }
                                    String data = "";
                                    data = out.toString();

                                    // YOUR CODE HERE [write string to data/data.json]
                                    //      [hint, i wrote a writeFile method in MyApp... figure out how to call that from inside this Activity]
                                    QuizApp quizApp = (QuizApp) getApplication();
                                    quizApp.setData(data);
                                    quizApp.writeToFile(data);
                                    // convert your json to a string and echo it out here to show that you did download it

                                    /*

                                    String jsonString = ....myjson...to string().... chipotle burritos.... blah
                                    Log.i("MyApp - Here is the json we download:", jsonString);

                                    */

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case DownloadManager.STATUS_FAILED:
                                // YOUR CODE HERE! Your download has failed! Now what do you want it to do? Retry? Quit application? up to you!
                                break;
                        }
                    }
                }
            }
        }
    };

    // reads InputStream of JSON file and returns the file in JSON String format
    public String readJSONFile(InputStream inputStream) throws IOException {

        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, "UTF-8");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent next = new Intent(MainActivity.this, UserSettingActivity.class);
            startActivity(next);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SETTINGS_RESULT)
        {
            //displayUserSettings();
        }

    }
}
