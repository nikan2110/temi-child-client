package il.meuhedet.childtemiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import il.meuhedet.childtemiapplication.utils.Constants;
import il.meuhedet.childtemiapplication.utils.QuestionForChildSurvey;

public class ChildSurveyActivity extends AppCompatActivity {

    private ArrayList<QuestionForChildSurvey> questions;
    private int currentQuestionIndex = 0;
    private TextView questionDescription;
    private TextView mainQuestion;
    private RelativeLayout mainBackground;
    private ImageButton buttonYes;
    private ImageButton buttonNo;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_survey);

        questionDescription = findViewById(R.id.questionDescription);
        mainQuestion = findViewById(R.id.mainQuestion);
        mainBackground = findViewById(R.id.mainBackground);

        buttonYes = findViewById(R.id.yesButton);
        buttonNo = findViewById(R.id.noButton);
        backButton = findViewById(R.id.backButton);

        questions = Constants.getQuestionsForChildSurvey();

        loadQuestion();

        buttonYes.setOnClickListener(v -> {
            buttonYes.setImageResource(R.drawable.button_yes_on);

            buttonYes.postDelayed(() -> {
                buttonYes.setImageResource(R.drawable.button_yes);
                nextQuestion();
            }, 200);
        });

        buttonNo.setOnClickListener(v -> {
            buttonNo.setImageResource(R.drawable.button_no_on);

            buttonNo.postDelayed(() -> {
                buttonNo.setImageResource(R.drawable.button_no);
                nextQuestion();
            }, 200);
        });

        backButton.setOnClickListener(view -> {
            backButton.setImageResource(R.drawable.button_back_on);
            finish();
            Intent intent = new Intent(ChildSurveyActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            QuestionForChildSurvey question = questions.get(currentQuestionIndex);

            questionDescription.setText(question.getQuestionDescription());
            mainQuestion.setText(question.getQuestionText());
            int backgroundResource = getResources().getIdentifier(
                    question.getImageName(),
                    "drawable",
                    getPackageName()
            );
            mainBackground.setBackgroundResource(backgroundResource);
        } else {
            Toast.makeText(this, "Questions were finished", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextQuestion() {
        buttonYes.setImageResource(R.drawable.button_yes);
        buttonNo.setImageResource(R.drawable.button_no);
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            loadQuestion();
        } else {
            Toast.makeText(this, "Questions were finished", Toast.LENGTH_SHORT).show();
        }
    }
}