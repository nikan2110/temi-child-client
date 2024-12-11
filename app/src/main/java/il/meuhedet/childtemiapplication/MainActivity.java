package il.meuhedet.childtemiapplication;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private boolean isPlaying = false;
    private MediaPlayer mediaPlayer;
    private ImageButton hebrewButton;
    private TextView hebrewTextButton;
    private ImageButton russianButton;
    private TextView russianTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton musicButton = findViewById(R.id.musicButton);
        musicButton.setOnClickListener(view -> {
            musicButton.setImageResource(R.drawable.button_sing_on);
            Intent intent = new Intent(MainActivity.this,
                    ChildCartoonActivity.class);
            startActivity(intent);
        });

        hebrewButton = findViewById(R.id.hebButtonBackground);
        russianButton = findViewById(R.id.rusButtonBackground);

        hebrewTextButton = findViewById(R.id.hebButtonText);
        russianTextButton = findViewById(R.id.rusButtonText);

        hebrewButton.setOnClickListener(view -> {
            russianTextButton.setTextColor(Color.parseColor("#848484"));
            russianButton.setImageResource(R.drawable.button_language_off);
            hebrewTextButton.setTextColor(Color.parseColor("#FFFFFF"));
            hebrewButton.setImageResource(R.drawable.button_language_on);
        });

        russianButton.setOnClickListener(view -> {
            hebrewTextButton.setTextColor(Color.parseColor("#848484"));
            hebrewButton.setImageResource(R.drawable.button_language_off);
            russianButton.setImageResource(R.drawable.button_language_on);
            russianTextButton.setTextColor(Color.parseColor("#FFFFFF"));
        });

        ImageButton danceButton = findViewById(R.id.danceButton);
        mediaPlayer = MediaPlayer.create(this, R.raw.robot_dance);

        danceButton.setOnClickListener(view -> {
            if (isPlaying) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                danceButton.setImageResource(R.drawable.button_dance);
                TextView cornetText = findViewById(R.id.topRightText);
                cornetText.setText("בוא לרקוד אתי");
            } else {
                mediaPlayer.start();
                danceButton.setImageResource(R.drawable.button_dance_on);
            }
            isPlaying = !isPlaying;
        });

        ImageButton sekerButton = findViewById(R.id.sekerButton);
        sekerButton.setOnClickListener(view -> {
            sekerButton.setImageResource(R.drawable.button_seker_on);
            Intent intent = new Intent(MainActivity.this,
                    CustomerServiceSurveyActivity.class);
            startActivity(intent);
        });

        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(view -> {
            EditText ageTextInput = findViewById(R.id.ageInput);
            ageTextInput.setText("");
        });

        ImageButton chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(view -> {
            chatButton.setImageResource(R.drawable.button_talk_on);
            Intent intent = new Intent(MainActivity.this,
                    ChildStoryActivity.class);
            finish();
            startActivity(intent);
        });

        ImageButton childSurveyButton = findViewById(R.id.childSurveyButton);
        childSurveyButton.setOnClickListener(view -> {
            childSurveyButton.setImageResource(R.drawable.button_parents_on);
            Intent intent = new Intent(MainActivity.this,
                    ChildSurveyActivity.class);
            startActivity(intent);
        });

    }
}