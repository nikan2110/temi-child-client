package il.meuhedet.childtemiapplication;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.SttLanguage;
import com.robotemi.sdk.SttRequest;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.constants.SdkConstants;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import il.meuhedet.childtemiapplication.utils.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChildStoryActivity extends AppCompatActivity
        implements OnRobotReadyListener, Robot.AsrListener, Robot.TtsListener  {

    private ProgressBar progressBar;
    private ImageButton backButton;
    private Robot robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_story);

        robot = Robot.getInstance();
        robot.setKioskModeOn(true);

        progressBar = findViewById(R.id.progressBar);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(view -> {
            backButton.setImageResource(R.drawable.button_back_on);
            finish();
            Intent intent = new Intent(ChildStoryActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        robot.addOnRobotReadyListener(this);
        robot.addAsrListener(this);
        robot.addTtsListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        robot.removeOnRobotReadyListener(this);
        robot.removeAsrListener(this);
        robot.removeTtsListener(this);
    }

    private void sendNameToServer(String message) {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Data generation error",
                        Toast.LENGTH_SHORT).show();
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
                .addHeader("language", "he")
                .post(body)
                .build();

        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String story = jsonResponse.getString("reply");

                    Log.i("story", story);

                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                            robot.speak(TtsRequest.create(story, false,
                                    TtsRequest.Language.HE_IL, true));

                    });
                } else {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Server error: " + response.code(),
                                Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (IOException | JSONException e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                    Toast.makeText(this, "Server connection error",
                            Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }


    @Override
    public void onAsrResult(@NonNull String asrResult, @NonNull SttLanguage sttLanguage) {
        Log.i("sttLanguage", sttLanguage.name());
        Log.i("AsrResult", "Received asrResult: " + asrResult);
        if (!asrResult.isEmpty()) {
            sendNameToServer(asrResult);
        }
    }

    @Override
    public void onTtsStatusChanged(@NonNull TtsRequest ttsRequest) {
        Log.i("Status", ttsRequest.getStatus().toString());
        if (ttsRequest.getStatus() == TtsRequest.Status.COMPLETED) {
            finish();
        }
    }

    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            speakGreeting();
        }
    }

    private void speakGreeting() {
        String greetingText = "";
        TtsRequest ttsRequest = null;
        List<SttLanguage> languages = new ArrayList<>();

            greetingText = "היי, תגיד לי את שמך ואני אספר לך סיפור.";
            ttsRequest = TtsRequest.create(greetingText,
                    false, TtsRequest.Language.HE_IL, true);
            languages.add(SttLanguage.IW_IL);
            robot.setAsrLanguages(languages);

            SttRequest sttRequest = new SttRequest(Collections.singletonList(SttLanguage.IW_IL),
                    120, false
            );
            robot.askQuestion(ttsRequest,sttRequest);

    }
}