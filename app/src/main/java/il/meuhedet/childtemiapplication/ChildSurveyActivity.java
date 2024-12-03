package il.meuhedet.childtemiapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import il.meuhedet.childtemiapplication.utils.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChildSurveyActivity extends AppCompatActivity {

    private TextView questionText;
    private EditText ageInput, additionalInfoInput;
    private Button yesButton, noButton, submitButton, nextButton;
    private View yesNoButtonsLayout;
    private TextView resultText;
    private ProgressBar progressBar;
    private int currentQuestionIndex = 0;

    private final String[] questions = {
            "בן כמה אתה?",
            "מיומנויות מוטוריות גסות: האם הילד שלך יכול לקפוץ על רגל אחת מבלי לאבד שיווי משקל?",
            "מיומנויות מוטוריות עדינות: האם הילד שלך יכול לצייר דמות עם לפחות שלושה חלקי גוף?",
            "מיומנויות שפה: האם הילד שלך יכול לספר סיפור פשוט באמצעות משפטים מלאים?",
            "מיומנויות חברתיות: האם הילד שלך משחק בשיתוף פעולה עם ילדים אחרים, כמו לקחת תורות ולשתף צעצועים?",
            "מיומנויות טיפול עצמי: האם הילד שלך יכול להתלבש בעצמו ללא עזרה, כולל לנעול נעליים וגרביים?",
            "מיומנויות פתרון בעיות: האם הילד שלך יכול להשלים פאזל פשוט (למשל, 4-6 חלקים) ללא עזרה?"
    };
    private final String[] answers = new String[questions.length + 1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_survey);

        resultText = findViewById(R.id.resultText);
        progressBar = findViewById(R.id.progressBar);

        questionText = findViewById(R.id.questionText);
        ageInput = findViewById(R.id.ageInput);
        additionalInfoInput = findViewById(R.id.additionalInfoInput);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        yesNoButtonsLayout = findViewById(R.id.yesNoButtonsLayout);

        loadQuestion();

        nextButton.setOnClickListener(view -> handleNext());
        yesButton.setOnClickListener(view -> handleAnswer("כן"));
        noButton.setOnClickListener(view -> handleAnswer("לא"));
        submitButton.setOnClickListener(view -> submitSurvey());
    }

    private void loadQuestion() {
        ageInput.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        yesNoButtonsLayout.setVisibility(View.GONE);
        additionalInfoInput.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);

        if (currentQuestionIndex == 0) {
            questionText.setText(questions[currentQuestionIndex]);
            ageInput.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            ageInput.setText("");
        } else if (currentQuestionIndex < questions.length) {
            questionText.setText(questions[currentQuestionIndex]);
            yesNoButtonsLayout.setVisibility(View.VISIBLE);
        } else {
            questionText.setText("הזן מידע נוסף:");
            additionalInfoInput.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.VISIBLE);
            additionalInfoInput.setText("");
        }
    }

    private void handleNext() {
        String age = ageInput.getText().toString().trim();
        if (age.isEmpty()) {
            Toast.makeText(this, "הזן את הגיל שלך!", Toast.LENGTH_SHORT).show();
            return;
        }
        answers[currentQuestionIndex] = age;
        currentQuestionIndex++;
        loadQuestion();
    }

    private void handleAnswer(String answer) {
        answers[currentQuestionIndex] = answer;
        currentQuestionIndex++;
        loadQuestion();
    }

    private void submitSurvey() {
        String additionalInfo = additionalInfoInput.getText().toString().trim();
        if (additionalInfo.isEmpty()) {
            additionalInfo = "אין מידע נוסף";
        }
        answers[questions.length] = additionalInfo;

        progressBar.setVisibility(View.VISIBLE);
        hideElements();

        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            showResultImage();
        }, 2000);
    }

    private void showResultImage() {
        ImageView resultImage = findViewById(R.id.resultImage);
        resultImage.setVisibility(View.VISIBLE);
    }

    private void hideElements() {
        questionText.setVisibility(View.GONE);
        additionalInfoInput.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        yesNoButtonsLayout.setVisibility(View.GONE);
        ageInput.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
    }
}