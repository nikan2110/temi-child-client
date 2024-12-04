package il.meuhedet.childtemiapplication.utils;

import java.util.ArrayList;

public class Constants {
    public static final String OPTION1 = getEmojiByUnicode(0x1F60A) + " מרוצה במידה רבה מאוד ";
    public static final String OPTION2 = getEmojiByUnicode(0x1F60C) + " מרוצה במידה רבה ";
    public static final String OPTION3 = getEmojiByUnicode(0x1F614) + " מרוצה במידה בינונית ";
    public static final String OPTION4 = getEmojiByUnicode(0x1F621) + " כלל לא מרוצה ";

    public static final String URL = "http://10.100.102.128:8080/";

    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questionsList = new ArrayList<>();

        // 1
        Question question1 = new Question(1, "כיצד היית מדרג את שביעות רצונך מהשירות שניתן לך במרפאה?",
                OPTION1, OPTION2, OPTION3, OPTION4);
        questionsList.add(question1);

        // 2
        Question question2 = new Question(2, "כיצד היית מדרג את שביעות רצונך מהסדר והניקיון במרפאה?",
                OPTION1, OPTION2, OPTION3, OPTION4);
        questionsList.add(question2);

        // 3
        Question question3 = new Question(3, "כיצד היית מדרג את שביעות רצונך מזמן ההמתנה לקבלת שירות במרפאה?",
                OPTION1, OPTION2, OPTION3, OPTION4);
        questionsList.add(question3);

        // 4
        Question question4 = new Question(4, "כיצד היית מדרג את שביעות רצונך מהיחס והאדיבות של הרופא?",
                OPTION1, OPTION2, OPTION3, OPTION4);
        questionsList.add(question4);

        return questionsList;
    }

    private static String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
