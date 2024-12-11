package il.meuhedet.childtemiapplication.utils;

public class QuestionForCustomerSurvey {
    private int id;
    private String description;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;

    public QuestionForCustomerSurvey(int id,
                                     String description,
                                     String optionOne,
                                     String optionTwo,
                                     String optionThree,
                                     String optionFour) {
        this.id = id;
        this.description = description;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }


    public String getOptionOne() {
        return optionOne;
    }


    public String getOptionTwo() {
        return optionTwo;
    }


    public String getOptionThree() {
        return optionThree;
    }


    public String getOptionFour() {
        return optionFour;
    }


    @Override
    public String toString() {
        return "QuestionForCustomerSurvey{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", optionOne='" + optionOne + '\'' +
                ", optionTwo='" + optionTwo + '\'' +
                ", optionThree='" + optionThree + '\'' +
                ", optionFour='" + optionFour + '\'' +
                '}';
    }
}
