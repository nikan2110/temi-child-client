package il.meuhedet.childtemiapplication.utils;

import java.util.ArrayList;

public class Constants {
    public static final String OPTION1 = getEmojiByUnicode(0x1F60A) + " מרוצה במידה רבה מאוד ";
    public static final String OPTION2 = getEmojiByUnicode(0x1F60C) + " מרוצה במידה רבה ";
    public static final String OPTION3 = getEmojiByUnicode(0x1F614) + " מרוצה במידה בינונית ";
    public static final String OPTION4 = getEmojiByUnicode(0x1F621) + " כלל לא מרוצה ";

    public static final String URL = "http://10.100.102.128:8080/";

    public static ArrayList<QuestionForCustomerSurvey> getQuestionsForCustomerSurvey() {
        ArrayList<QuestionForCustomerSurvey> questionsList = new ArrayList<>();

        // 1
        QuestionForCustomerSurvey questionForCustomerSurvey1 = new QuestionForCustomerSurvey(1,
                "כיצד היית מדרג את שביעות רצונך מהשירות שניתן לך במרפאה?",
                OPTION1, OPTION2, OPTION3, OPTION4);
        questionsList.add(questionForCustomerSurvey1);

        // 2
        QuestionForCustomerSurvey questionForCustomerSurvey2 = new QuestionForCustomerSurvey(2,
                "כיצד היית מדרג את שביעות רצונך מהסדר והניקיון במרפאה?",
                OPTION1, OPTION2, OPTION3, OPTION4);
        questionsList.add(questionForCustomerSurvey2);

        // 3
        QuestionForCustomerSurvey questionForCustomerSurvey3 = new QuestionForCustomerSurvey(3,
                "כיצד היית מדרג את שביעות רצונך מזמן ההמתנה לקבלת שירות במרפאה?",
                OPTION1, OPTION2, OPTION3, OPTION4);
        questionsList.add(questionForCustomerSurvey3);

        // 4
        QuestionForCustomerSurvey questionForCustomerSurvey4 = new QuestionForCustomerSurvey(4,
                "כיצד היית מדרג את שביעות רצונך מהיחס והאדיבות של הרופא?",
                OPTION1, OPTION2, OPTION3, OPTION4);
        questionsList.add(questionForCustomerSurvey4);

        return questionsList;
    }

    public static ArrayList<QuestionForChildSurvey> getQuestionsForChildSurvey() {
        ArrayList<QuestionForChildSurvey> questionListForChildSurvey = new ArrayList<>();
        QuestionForChildSurvey questionForChildSurvey1 = new QuestionForChildSurvey(
                "לקפוץ על רגל אחת",
                "האם הילד שלך יכול לקפוץ על רגל אחת מבלי לאבד שיווי משקל?",
                "first_question");

        QuestionForChildSurvey questionForChildSurvey2 = new QuestionForChildSurvey(
                "ציור",
                "האם הילד שלך יכול לצייר דמות עם לפחות שלושה חלקי גוף?",
                "second_question");

        QuestionForChildSurvey questionForChildSurvey3 = new QuestionForChildSurvey(
                "סיפור פשוט",
                "האם הילד שלך יכול לספר סיפור פשוט באמצעות משפטים מלאים?",
                "third_question");

        QuestionForChildSurvey questionForChildSurvey4 = new QuestionForChildSurvey(
                "חברים",
                "האם הילד שלך משחק בשיתוף פעולה עם ילדים אחרים, כמו לקחת תורות ולשתף צעצועים?",
                "fourth_question");

        questionListForChildSurvey.add(questionForChildSurvey1);
        questionListForChildSurvey.add(questionForChildSurvey2);
        questionListForChildSurvey.add(questionForChildSurvey3);
        questionListForChildSurvey.add(questionForChildSurvey4);

        return questionListForChildSurvey;
    }

    private static String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
