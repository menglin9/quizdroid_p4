package quiz_p4.menglin9.washington.edu.quiz_p4;

import java.util.ArrayList;

/**
 * Created by Menglin on 5/10/15.
 */
public class Topic {

    public String title;
    public String shortD;
    public String longD;
    public ArrayList<Quiz> quiz;
    public int icon;

    public Topic() {
        quiz = new ArrayList<Quiz>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShortD(String shortD) {
        this.shortD = shortD;
    }

    public void setLongD(String longD) {
        this.longD = longD;
    }

    public void setQuizList(ArrayList<Quiz> quiz) {
        this.quiz = quiz;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getShortD() {
        return shortD;
    }

    public String getLongD() {
        return longD;
    }

    public ArrayList<Quiz> getQuiz() {
        return quiz;
    }

    public int getIcon() {return  icon;}
}