package il.meuhedet.childtemiapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import il.meuhedet.childtemiapplication.utils.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChildStoryActivity extends AppCompatActivity {

    private TextView storyText;
    private EditText nameInput;
    private Button submitButton;
    private ProgressBar progressBar; // Добавляем ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_story);

        storyText = findViewById(R.id.storyText);
        nameInput = findViewById(R.id.nameInput);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);

        Toast.makeText(this, "Tell me your name and I'll tell you a story about you!",
                Toast.LENGTH_LONG).show();

        submitButton.setOnClickListener(view -> {
            String name = nameInput.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            nameInput.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);

            progressBar.setVisibility(View.VISIBLE);

            sendNameToServer(name);
        });
    }

    private void sendNameToServer(String message) {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Data generation error", Toast.LENGTH_SHORT).show();
            });
            return;
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(requestBody.toString(), JSON);

        Request request = new Request.Builder()
                .url(Constants.URL + "child_chat")
                .post(body)
                .build();

        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String story = jsonResponse.getString("reply");

                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        showStory(story);
                    });
                } else {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (IOException | JSONException e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                    Toast.makeText(this, "Server connection error", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void showStory(String story) {

        storyText.setVisibility(View.VISIBLE);
        storyText.setText(story);
    }
}