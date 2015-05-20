package quiz_p4.menglin9.washington.edu.quiz_p4;

/**
 * Created by Menglin on 5/17/15.
 */
import java.util.ArrayList;

public class Quiz {

    public String text;
    public ArrayList<String> answers;
    public int correct;

    public Quiz() {
        answers = new ArrayList<String>();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public int getCorrect() {
        return correct;
    }

    public String getAnswer() {
        return answers.get(correct - 1);
    }

}