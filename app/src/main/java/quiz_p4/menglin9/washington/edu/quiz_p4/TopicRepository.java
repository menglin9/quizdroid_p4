package quiz_p4.menglin9.washington.edu.quiz_p4;

import java.util.ArrayList;

/**
 * Created by Menglin on 5/10/15.
 */
public interface TopicRepository {

    public String[] getAllTopics();

    public String titleName(int i);

    public String longDescription(int i);

    //return questionNum of given topic i
    public int questionNum(int i);

    //return the current question in topic i and question index j
    public String getCurrentQuestion(int i, int j);

    //return all the answers as Arraylist of certain topic i and certain question j
    public ArrayList<String> getAllAnswers(int i, int j);

    //return correct answer of certain topic i and certain question j
    public String getCorrectAnswer(int i, int j);

    public int[] getAllIcons();

    public int getIcons(int i);
}
