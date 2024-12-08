package il.meuhedet.childtemiapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import il.meuhedet.childtemiapplication.utils.Constants;
import il.meuhedet.childtemiapplication.utils.QuestionForCustomerSurvey;

public class CustomerServiceSurveyActivity extends AppCompatActivity implements View.OnClickListener {

    private int currentPosition = 1;
    private ArrayList<QuestionForCustomerSurvey> questionForCustomerSurveyList;
    private LinearLayout selectedOption = null; // Ссылка на выбранный вариант

    private ProgressBar progressBar;
    private TextView tvProgress;
    private TextView tvQuestion;

    private LinearLayout llOptionOne;
    private LinearLayout llOptionTwo;
    private LinearLayout llOptionThree;
    private LinearLayout llOptionFour;

    private Button btnSubmitQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_survey);

        // Инициализация элементов
        progressBar = findViewById(R.id.progress_bar);
        tvProgress = findViewById(R.id.tv_progress);
        tvQuestion = findViewById(R.id.tv_question);

        llOptionOne = findViewById(R.id.ll_option_one);
        llOptionTwo = findViewById(R.id.ll_option_two);
        llOptionThree = findViewById(R.id.ll_option_three);
        llOptionFour = findViewById(R.id.ll_option_four);

        btnSubmitQuestion = findViewById(R.id.btn_submit_question);

        // Установка слушателей
        llOptionOne.setOnClickListener(this);
        llOptionTwo.setOnClickListener(this);
        llOptionThree.setOnClickListener(this);
        llOptionFour.setOnClickListener(this);
        btnSubmitQuestion.setOnClickListener(this);

        questionForCustomerSurveyList = Constants.getQuestionsForCustomerSurvey();
        setQuestion();
    }

    private void setQuestion() {
        Log.i("Survey", "Loading question " + currentPosition);

        // Сбрасываем выделение
        resetOptionSelection();

        // Получаем текущий вопрос
        QuestionForCustomerSurvey question = questionForCustomerSurveyList.get(currentPosition - 1);

        // Устанавливаем данные вопроса
        tvQuestion.setText(question.getDescription());
        progressBar.setProgress(currentPosition);
        tvProgress.setText(currentPosition + "/" + progressBar.getMax());

        // Устанавливаем текст для вариантов
        ((TextView) llOptionOne.findViewById(R.id.tv_option_one)).setText(question.getOptionOne());
        ((TextView) llOptionTwo.findViewById(R.id.tv_option_two)).setText(question.getOptionTwo());
        ((TextView) llOptionThree.findViewById(R.id.tv_option_three)).setText(question.getOptionThree());
        ((TextView) llOptionFour.findViewById(R.id.tv_option_four)).setText(question.getOptionFour());
    }

    private void resetOptionSelection() {
        // Сбрасываем фон всех вариантов на стандартный
        resetButtonBackground(llOptionOne);
        resetButtonBackground(llOptionTwo);
        resetButtonBackground(llOptionThree);
        resetButtonBackground(llOptionFour);

        selectedOption = null; // Сбрасываем выбранный вариант
    }

    private void resetButtonBackground(LinearLayout button) {
        // Клонируем Drawable, чтобы сброс был уникальным для каждой кнопки
        Drawable drawable = getResources().getDrawable(R.drawable.button_option).mutate();
        button.setBackground(drawable);
    }

    private void selectOption(LinearLayout option) {
        resetOptionSelection(); // Сбрасываем фон всех вариантов

        // Устанавливаем основной фон
        Drawable drawable = getResources().getDrawable(R.drawable.button_option).mutate();
        option.setBackground(drawable);

        // Накладываем дополнительный цвет поверх основного фона
        option.getBackground().setColorFilter(Color.parseColor("#D3D8D1"), PorterDuff.Mode.MULTIPLY);

        selectedOption = option; // Устанавливаем текущий выбранный вариант
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_option_one) {
            selectOption(llOptionOne);
        } else if (view.getId() == R.id.ll_option_two) {
            selectOption(llOptionTwo);
        } else if (view.getId() == R.id.ll_option_three) {
            selectOption(llOptionThree);
        } else if (view.getId() == R.id.ll_option_four) {
            selectOption(llOptionFour);
        } else if (view.getId() == R.id.btn_submit_question) {
            if (selectedOption == null) {
                Log.i("Survey", "No option selected");
                return; // Если ничего не выбрано, ничего не делаем
            }

            // Логика перехода к следующему вопросу
            if (currentPosition < questionForCustomerSurveyList.size()) {
                currentPosition++;
                setQuestion();
            } else {
                Log.i("Survey", "Survey completed");
                Intent intent = new Intent(this, MainActivity.class); // Переход на главную
                startActivity(intent);
            }
        }
    }
}