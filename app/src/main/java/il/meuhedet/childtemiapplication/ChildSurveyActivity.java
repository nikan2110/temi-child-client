package il.meuhedet.childtemiapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        }
    }

    private void nextQuestion() {
        buttonYes.setImageResource(R.drawable.button_yes);
        buttonNo.setImageResource(R.drawable.button_no);
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            loadQuestion();
        } else {
            showRecommendation();
        }
    }

    private void showRecommendation() {
        int backgroundResource = getResources()
                .getIdentifier("recommendation", "drawable", getPackageName());
        mainBackground.setBackgroundResource(backgroundResource);

        buttonYes.setVisibility(View.INVISIBLE);
        buttonNo.setVisibility(View.INVISIBLE);

        questionDescription.setText("המלצות");
        mainQuestion.setText("כמה המלצות שיכולות לעזור");

        LinearLayout recommendationsContainer = findViewById(R.id.recommendationsContainer);
        recommendationsContainer.setVisibility(View.VISIBLE);
        recommendationsContainer.removeAllViews();

        String[] recommendations = {
                "התייעצות עם מומחה: כדאי לפנות לרופא\n ילדים או פיזיותרפיסט שמתמחה\n בהתפתחות הילד",
                "תרגול בבית :עודדו את הילד להשתתף\n בפעילויות שמפתחות מיומנויות מוטוריות\n גסות, כמו קפיצה על טרמפולינה ריצה ומשחקי תופסת",
                "פעילויות ספורטיביות: הרשמה לחוגי ספורט\n כמו שחייה, כדורגל או ריקוד יכולה לעזור בשיפור\n הקואורדינציה והאיזון"
        };

        for (String recommendation : recommendations) {

            LinearLayout recommendationLayout = new LinearLayout(this);
            recommendationLayout.setOrientation(LinearLayout.HORIZONTAL);
            recommendationLayout.setPadding(0, 16, 0, 16);
            recommendationLayout.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);


            TextView recommendationView = new TextView(this);
            recommendationView.setText(recommendation);
            recommendationView.setTextSize(42);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                recommendationView.setLineHeight(55);
            }
            recommendationView.setTextColor(Color.BLACK);
            recommendationView.setGravity(Gravity.CENTER_VERTICAL);
            recommendationView.setSingleLine(false);
            recommendationView.setMaxLines(3);
            recommendationView.setEllipsize(TextUtils.TruncateAt.END);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textParams.setMargins(0, 0, 16, 0);
            recommendationView.setLayoutParams(textParams);

            ImageView circleIcon = new ImageView(this);
            circleIcon.setImageResource(R.drawable.circle_icon);
            LinearLayout.LayoutParams circleParams = new LinearLayout.LayoutParams(24, 24);
            circleIcon.setLayoutParams(circleParams);

            recommendationLayout.addView(recommendationView);
            recommendationLayout.addView(circleIcon);

            recommendationsContainer.addView(recommendationLayout);
        }
    }
}