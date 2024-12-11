package il.meuhedet.childtemiapplication.utils;

import java.util.Objects;

public class QuestionForChildSurvey {
    String questionDescription;
    String questionText;
    String imageName;

    public QuestionForChildSurvey(String questionDescription,
                                  String questionText,
                                  String imageName) {
        this.questionDescription = questionDescription;
        this.questionText = questionText;
        this.imageName = imageName;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getImageName() {
        return imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionForChildSurvey that = (QuestionForChildSurvey) o;
        return Objects.equals(questionDescription, that.questionDescription)
                && Objects.equals(questionText, that.questionText)
                && Objects.equals(imageName, that.imageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionDescription, questionText, imageName);
    }

    @Override
    public String toString() {
        return "QuestionForChildSurvey{" +
                "questionDescription='" + questionDescription + '\'' +
                ", questionText='" + questionText + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
