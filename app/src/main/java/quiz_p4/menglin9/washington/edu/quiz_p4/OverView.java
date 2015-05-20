package quiz_p4.menglin9.washington.edu.quiz_p4;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverView extends Fragment {

    private Activity hostActivity;
    int position = 0;

    public OverView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_over_view, container, false);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView intro = (TextView) v.findViewById(R.id.intro);
        ImageView image = (ImageView) v.findViewById(R.id.image);

        // load title, intro, image for certain list items
        if (position == 0) {
            title.setText("Math");
            intro.setText("Description: The math problem is design for the primary school intelligent competition practise. The Problem has 5 problems total. Have Fun!");
            //image.setImageResource(R.drawable.math);
        } else if (position == 1) {
            title.setText("Physics");
            intro.setText("Description: The physics is design for University physics. The Problem has 5 problems total. Have Fun!");
            //image.setImageResource(R.drawable.physics);
        } else if (position == 2) {
            title.setText("Unix");
            intro.setText("Description: The Unix test is design for reviewing content for cse 390 in UW. The Problem has 5 problems total. Have Fun!");
            //image.setImageResource(R.drawable.unix);
        }

        // get your Application singleton
        QuizApp quizApp = (QuizApp) getActivity().getApplication();
        if (quizApp.getSuccess()) {
            for (int i = 0; i < quizApp.topicNum(); i++) {
                if (position == i) {
                    title.setText("" + quizApp.titleName(i));
                    intro.setText("Description: " + quizApp.longDescription(i));
                    image.setImageResource(quizApp.getIcons(i));
                }
            }
        }

        Button btn = (Button) v.findViewById(R.id.begin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hostActivity instanceof Quiz_Content) {
                    ((Quiz_Content) hostActivity).loadQuestionFragment();
                }
            }
        });

        return v;
    }

    // get the host activity for the fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
    }
}
