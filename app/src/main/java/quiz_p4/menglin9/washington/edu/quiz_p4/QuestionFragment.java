package quiz_p4.menglin9.washington.edu.quiz_p4;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    HashMap<String, String> questionMap;
    HashMap<String, String[]> choices;
    int position = 0;
    int qNum = 0;
    String Qtag = "";
    Button radioButton;
    private Activity hostActivity;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vi = inflater.inflate(R.layout.fragment_question, container, false);

        // get your Application singleton
        QuizApp quizApp = (QuizApp) getActivity().getApplication();

        // put data into hashmap
        getData(quizApp);

        if (getArguments() != null) {
            position = getArguments().getInt("position");
            qNum = getArguments().getInt("qNum");
        }

        // get your Application singleton
        //QuizApp quizApp = (QuizApp) getActivity().getApplication();

        if (quizApp.getSuccess()) {
            for (int i = 0; i < quizApp.topicNum(); i++) {
                if (position == i) {
                    Qtag = "" + quizApp.titleName(i) + qNum;
                }
            }
        } else {

            // build the question tag
            if (position == 0) {
                Qtag = "M" + qNum;
            } else if (position == 1) {
                Qtag = "P" + qNum;
            } else if (position == 2) {
                Qtag = "U" + qNum;
            }

        }
        // change the question according to the tag
        TextView Q = (TextView) vi.findViewById(R.id.Q_title);
        Q.setText(questionMap.get(Qtag));

        RadioButton B1 = (RadioButton) vi.findViewById(R.id.answer1);
        RadioButton B2 = (RadioButton) vi.findViewById(R.id.answer2);
        RadioButton B3 = (RadioButton) vi.findViewById(R.id.answer3);
        RadioButton B4 = (RadioButton) vi.findViewById(R.id.answer4);

        B1.setText(choices.get(Qtag)[0]);
        B2.setText(choices.get(Qtag)[1]);
        B3.setText(choices.get(Qtag)[2]);
        B4.setText(choices.get(Qtag)[3]);

        final RadioGroup radioGroup = (RadioGroup) vi.findViewById(R.id.radioQuestionGroup);
        final Button submit = (Button) vi.findViewById(R.id.submitButton);
        //final Button radioButton;

        // show the submit button when any radio button has been selected
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                submit.setVisibility(View.VISIBLE);
            }
        });

        // submit button and pass it back to the activity
        //final RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radioQuestionGroup);
        //final Button submitButton = (Button) v.findViewById(R.id.submitButton);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // find the radio button by returned id
                int sId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) vi.findViewById(sId);
                // save the answer data
                String answerData = (String) radioButton.getText();
                //Log.e("ink", "enter0");
                if (hostActivity instanceof Quiz_Content) {

                    Quiz_Content origActivity = ((Quiz_Content) hostActivity);
                    origActivity.setAns(answerData);
                    origActivity.loadAnswerFragment();
                }
            }
        });
        return vi;
    }

    private void getData(QuizApp quizApp) {
        questionMap = new HashMap<String, String>();
        choices = new HashMap<String, String[]>();

        if (quizApp.getSuccess()) {


            for (int i = 0; i < quizApp.topicNum(); i++) {
                for (int j = 0; j < quizApp.questionNum(i); j++) {
                    questionMap.put("" + quizApp.titleName(i) + (j+1), "Q" + (j+1) + ": " + quizApp.getCurrentQuestion(i, j));
                    choices.put("" + quizApp.titleName(i) + (j+1), new String[]{quizApp.getAllAnswers(i,j).get(0), quizApp.getAllAnswers(i,j).get(1), quizApp.getAllAnswers(i,j).get(2), quizApp.getAllAnswers(i,j).get(3)});
                }
            }


        } else {
            questionMap.put("M1", "Q1: Bob's father could cut his five nails in 1 minutes. So how many nails can he cut in 5 minutes?");
            questionMap.put("M2", "Q2: Three children eat three apples for three minutes. How long will take for ninety children eat ninety apples?");
            questionMap.put("M3", "Q3: Filling blank: 1 2 4 7 __ 16 22?");
            questionMap.put("M4", "Q4: In the grass land, one sheep will take 1 year to eat half of the grass in the land, how long will take for him to finish the whole grass land?");
            questionMap.put("M5", "Q5: there is a room and each corner has a cat. And there are three cats are in front of each cat, how many cats are in total in the room?");
            questionMap.put("P1", "Q1: A car travels 90. meters due north in 15 seconds. Then the car turns around and travels 40. meters due south in 5.0 seconds. What is the magnitude of the average velocity of the car during this 20.-second interval?");
            questionMap.put("P2", "Q2: How far will a brick starting from rest fall freely in 3.0 seconds?");
            questionMap.put("P3", "Q3: age force acti1,200-kilogram car traveling at 10. meters per second hits a tree and is brought to rest in 0.10 second. What is the magnitude of the averng on the car to bring it to rest?");
            questionMap.put("P4", "Q4: An object weighing 15 newtons is lifted from the ground to a height of 0.22 meter. The increase in the object’s gravitational potential energy is approximately?");
            questionMap.put("P5", "Q5: A spring scale reads 20. newtons as it pulls a 5.0-kilogram mass across a table. What is the magnitude of the force exerted by the mass on the spring scale?");
            questionMap.put("U1", "Q1: Which symbol will be used with grep command to match the pattern pat at the beginning of a line?");
            questionMap.put("U2", "Q2: Which command is used to sort the lines of data in a file in reverse order?");
            questionMap.put("U3", "Q3: Which command is used to display the top of the file?");
            questionMap.put("U4", "Q4: Which command is used to change protection mode of files starting with the string emp and ending with 1,2, or 3?");
            questionMap.put("U5", "Q5: Which command is used to remove the read permission of the file 'note' from both the group and others?");

            choices.put("M1", new String[]{"25", "20", "10", "5"});
            choices.put("M2", new String[]{"1", "3", "30", "90"});
            choices.put("M3", new String[]{"9", "11", "13", "15"});
            choices.put("M4", new String[]{"1 year", "2 years", "100 years", "None"});
            choices.put("M5", new String[]{"4", "3", "12", "7"});
            choices.put("P1", new String[]{"2.5 m/s", "5 m/s", "6.5 m/s", "7 m/s"});
            choices.put("P2", new String[]{"15 m/s", "29 m/s", "44 m/s", "88 m/s"});
            choices.put("P3", new String[]{"1.2 × 10^2 N", "1.2 × 10^3 N", "1.2 × 10^4 N", "1.2 × 10^5 N"});
            choices.put("P4", new String[]{"310 J", "32 J", "3.3 J", "0.34 J"});
            choices.put("P5", new String[]{"49 N", "20 N", "5 N", "4 N"});
            choices.put("U1", new String[]{"^pat", "$pat", "pat$", "pat^"});
            choices.put("U2", new String[]{"sort", "sh", "st", "sort -r"});
            choices.put("U3", new String[]{"cat", "head", "more", "grep"});
            choices.put("U4", new String[]{"chmod u+x emp[1-3]", "chmod 777 emp*", "chmod u+r ??? emp", "chmod 222 emp?"});
            choices.put("U5", new String[]{"chmod go+r note", "chmod go+rw note", "chmod go-x note", "chmod go-r note"});
        }
    }

    // get the host activity for the fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.hostActivity = activity;
    }


}
