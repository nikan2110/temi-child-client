package il.meuhedet.childtemiapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private boolean isPlaying = false;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton musicButton = findViewById(R.id.musicButton);
        musicButton.setOnClickListener(view -> {
            musicButton.setImageResource(R.drawable.button_sing_on);
            Intent intent = new Intent(MainActivity.this, ChildCartoonActivity.class);
            startActivity(intent);
        });

        ImageButton danceButton = findViewById(R.id.danceButton);
        mediaPlayer = MediaPlayer.create(this, R.raw.robot_dance);

        danceButton.setOnClickListener(view -> {
            if (isPlaying) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                danceButton.setImageResource(R.drawable.button_dance);
            } else {
                mediaPlayer.start();
                danceButton.setImageResource(R.drawable.button_dance_on);
            }
            isPlaying = !isPlaying; // Переключаем состояние
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
            startActivity(intent);
        });



//        Button childSurveyButton = findViewById(R.id.childSurveyButton);
//        childSurveyButton.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, ChildSurveyActivity.class);
//            startActivity(intent);
//        });
//
//        Button customerSurveyButton = findViewById(R.id.customerSurveyButton);
//        customerSurveyButton.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, CustomerServiceSurveyActivity.class);
//            startActivity(intent);
//        });
//
//        Button chatWithGptForKidsButton = findViewById(R.id.childChatButton);
//        chatWithGptForKidsButton.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, ChildStoryActivity.class);
//            startActivity(intent);
//        });
//
//        Button childCartoonButton = findViewById(R.id.childCartoonButton);
//        childCartoonButton.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, ChildCartoonActivity.class);
//            startActivity(intent);
//        });


    }
}