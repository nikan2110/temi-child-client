package il.meuhedet.childtemiapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import il.meuhedet.childtemiapplication.utils.Constants;
import il.meuhedet.childtemiapplication.utils.Question;

public class CustomerServiceSurveyActivity extends AppCompatActivity implements View.OnClickListener {

    private int currentPosition = 1;
    private ArrayList<Question> questionList;
    private int selectedOptionPosition = 0;

    private ProgressBar progressBar;
    private TextView tvProgress;
    private TextView tvQuestion;

    private TextView tvOptionOne;
    private TextView tvOptionTwo;
    private TextView tvOptionThree;
    private TextView tvOptionFour;

    private Button btnSubmitQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_survey);

        progressBar = findViewById(R.id.progress_bar);
        tvProgress = findViewById(R.id.tv_progress);
        tvQuestion = findViewById(R.id.tv_question);
        tvOptionOne = findViewById(R.id.tv_option_one);
        tvOptionTwo = findViewById(R.id.tv_option_two);
        tvOptionThree = findViewById(R.id.tv_option_three);
        tvOptionFour = findViewById(R.id.tv_option_four);
        btnSubmitQuestion = findViewById(R.id.btn_submit_question);

        tvOptionOne.setOnClickListener(this);
        tvOptionTwo.setOnClickListener(this);
        tvOptionThree.setOnClickListener(this);
        tvOptionFour.setOnClickListener(this);
        btnSubmitQuestion.setOnClickListener(this);

        questionList = Constants.getQuestions();
        setQuestion();
    }

    private void setQuestion() {
        Log.i("Question list size is", String.valueOf(questionList.size()));
        defaultOptionsView();
        Question question = questionList.get(currentPosition - 1);
        progressBar.setProgress(currentPosition);
        tvProgress.setText(currentPosition + "/" + progressBar.getMax());
        tvQuestion.setText(question.getDescription());
        tvOptionOne.setText(question.getOptionOne());
        tvOptionTwo.setText(question.getOptionTwo());
        tvOptionThree.setText(question.getOptionThree());
        tvOptionFour.setText(question.getOptionFour());

        if (currentPosition == questionList.size()) {
            btnSubmitQuestion.setText("FINISH");
        } else {
            btnSubmitQuestion.setText("SUBMIT");
        }
    }

    private void defaultOptionsView() {
        ArrayList<TextView> options = new ArrayList<>();
        options.add(tvOptionOne);
        options.add(tvOptionTwo);
        options.add(tvOptionThree);
        options.add(tvOptionFour);

        for (TextView option : options) {
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(ContextCompat.getDrawable(this, R.drawable.default_option_border_bg));
        }
    }

    private void selectedOptionView(TextView tv, int selectedOptionNum) {
        defaultOptionsView();
        selectedOptionPosition = selectedOptionNum;
        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_option_one) {
            selectedOptionView(tvOptionOne, 1);
        } else if (view.getId() == R.id.tv_option_two) {
            selectedOptionView(tvOptionTwo, 2);
        } else if (view.getId() == R.id.tv_option_three) {
            selectedOptionView(tvOptionThree, 3);
        } else if (view.getId() == R.id.tv_option_four) {
            selectedOptionView(tvOptionFour, 4);
        } else if (view.getId() == R.id.btn_submit_question) {
            if (currentPosition <= questionList.size()) {
                // Увеличиваем позицию для следующего вопроса
                currentPosition++;

                if (currentPosition <= questionList.size()) {
                    setQuestion();
                } else {
                    // Если вопросов больше нет, завершаем анкету
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
            // Сбрасываем выбранный вариант
            selectedOptionPosition = 0;
        }
    }
}