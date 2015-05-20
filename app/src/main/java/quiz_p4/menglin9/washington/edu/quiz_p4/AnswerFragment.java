package quiz_p4.menglin9.washington.edu.quiz_p4;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerFragment extends Fragment {

    HashMap<String, String> answerMap;
    String Atag = "";
    int position;
    int aNum;
    String myAnswer;
    private Activity hostActivity;
    int correctData = 0;
    boolean finishTag = false;

    public AnswerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi = inflater.inflate(R.layout.fragment_answer, container, false);

        // get your Application singleton
        QuizApp quizApp = (QuizApp) getActivity().getApplication();

        getData(quizApp);

        // get data from activity
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            aNum = getArguments().getInt("aNum");
            myAnswer = getArguments().getString("myAnswer");
        }

        if (quizApp.getSuccess()) {
            for (int i = 0; i < quizApp.topicNum(); i++) {
                if (position == i) {
                    Atag = "" + quizApp.titleName(i) + aNum;
                }
            }
        } else {

            // build the question tag
            if (position == 0) {
                Atag = "M" + aNum;
            } else if (position == 1) {
                Atag = "P" + aNum;
            } else if (position == 2) {
                Atag = "U" + aNum;
            }
        }

        //get my answer text
        TextView myA = (TextView) vi.findViewById(R.id.yourAnswer);
        myA.setText("Your Answer is: " + myAnswer);

        //set correct answer
        TextView coA = (TextView) vi.findViewById(R.id.correctAnswer);
        coA.setText("The correct Answer is: " + answerMap.get(Atag));

        //load correct number
        if (hostActivity instanceof Quiz_Content) {
            final Quiz_Content origActivity = ((Quiz_Content) hostActivity);
            //get the correct data
            correctData = origActivity.getCo();
            if (myAnswer.equals(answerMap.get(Atag))) {
                correctData ++;
                origActivity.setCo(correctData);
            }

            TextView data = (TextView) vi.findViewById(R.id.dataRecord);
            if (quizApp.getSuccess()) {
                data.setText("You have " + correctData + " out of " + quizApp.questionNum(position) +" correct");
            } else {
                data.setText("You have " + correctData + " out of 5 correct");
            }
            //Button staff
            Button b = (Button) vi.findViewById(R.id.nextButtom);

            if (quizApp.getSuccess()) {
                if (Atag.equals("" + quizApp.titleName(0) + quizApp.questionNum(0)) | Atag.equals("" + quizApp.titleName(1) + quizApp.questionNum(1)) | Atag.equals("" + quizApp.titleName(2) + quizApp.questionNum(2))) {
                    finishTag = true;
                    b.setText("Finsh");
                }
            } else {
                if (Atag.equals("M5") | Atag.equals("U5") | Atag.equals("P5")) {
                    finishTag = true;
                    b.setText("Finsh");
                }
            }

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!finishTag) {
                        origActivity.loadQuestionFragment();
                    } else {
                        origActivity.startOver();
                    }
                }
            });
            // origActivity.loadAnswerFragment();
        }

        return vi;
    }

    private void getData(QuizApp quizApp) {
        answerMap = new HashMap<String, String>();

        if (quizApp.getSuccess()) {
            for (int i = 0; i < quizApp.topicNum(); i++) {
                for (int j = 0; j < quizApp.questionNum(i); j++) {
                    answerMap.put("" + quizApp.titleName(i) + (j+1), "" + quizApp.getCorrectAnswer(i, j));
                }
            }
        } else {
            answerMap.put("M1", "20");
            answerMap.put("M2", "3");
            answerMap.put("M3", "11");
            answerMap.put("M4", "None");
            answerMap.put("M5", "4");
            answerMap.put("P1", "2.5 m/s");
            answerMap.put("P2", "44 m/s");
            answerMap.put("P3", "1.2 × 10^5 N");
            answerMap.put("P4", "3.3 J");
            answerMap.put("P5", "20 N");
            answerMap.put("U1", "^pat");
            answerMap.put("U2", "sort -r");
            answerMap.put("U3", "head");
            answerMap.put("U4", "chmod u+x emp[1-3]");
            answerMap.put("U5", "chmod go-r note");
        }
    }

    // get the host activity for the fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.hostActivity = activity;
    }

}
