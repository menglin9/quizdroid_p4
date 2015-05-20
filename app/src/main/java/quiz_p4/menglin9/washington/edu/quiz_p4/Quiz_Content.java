package quiz_p4.menglin9.washington.edu.quiz_p4;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;


public class Quiz_Content extends ActionBarActivity {

    int position = 0;
    Integer qNum = 0;
    Integer aNum = 0;
    String answer = "";
    int correct = 0;
    HashMap<Integer, String> questionMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__content);

        Intent mainPage = getIntent();
        //int position = 0;
        if (mainPage != null) {
            position = mainPage.getIntExtra("position", 0);
        }
        loadOverview();
    }

    public void loadOverview() {
        FragmentManager overViewManager = getFragmentManager();
        FragmentTransaction overViewT = overViewManager.beginTransaction();

        Bundle topicBundle = new Bundle();
        topicBundle.putInt("position", position);

        //overView is a fragment
        OverView overViewFragment = new OverView();
        overViewFragment.setArguments(topicBundle);

        overViewT.add(R.id.container, overViewFragment);
        overViewT.commit();
    }

    public void loadQuestionFragment() {
        FragmentManager questionManager = getFragmentManager();
        FragmentTransaction questionT = questionManager.beginTransaction();

        qNum ++;
        Bundle topicBundle = new Bundle();
        topicBundle.putInt("position", position);
        topicBundle.putInt("qNum", qNum);

        //overView is a fragment
        QuestionFragment qFragment = new QuestionFragment();
        qFragment.setArguments(topicBundle);

        questionT.replace(R.id.container, qFragment);
        questionT.commit();
    }

    public void loadAnswerFragment() {
        FragmentManager answerManager = getFragmentManager();
        FragmentTransaction answerT = answerManager.beginTransaction();

        //Log.i("d", "enter");
        aNum ++;
        Bundle topicBundle = new Bundle();
        topicBundle.putInt("position", position);
        topicBundle.putInt("aNum", aNum);
        topicBundle.putString("myAnswer", answer);

        //overView is a fragment
        AnswerFragment aFragment = new AnswerFragment();
        aFragment.setArguments(topicBundle);

        answerT.replace(R.id.container, aFragment);
        answerT.commit();
    }

    private void getDate() {
        questionMap = new HashMap<Integer, String>();
    }

    public void setAns(String answer) {
        this.answer = answer;
    }

    public int getCo() {
        return correct;
    }

    public void setCo(int correct) {
        this.correct = correct;
    }

    public void startOver() {
        Intent next = new Intent(Quiz_Content.this, MainActivity.class);
        //next.putExtra("position", position);
        startActivity(next);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz__content, menu);
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
            Intent next = new Intent(Quiz_Content.this, UserSettingActivity.class);
            startActivity(next);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}