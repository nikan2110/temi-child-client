package il.meuhedet.childtemiapplication;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ChildCartoonActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button pausePlayButton;
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_cartoon);

        videoView = findViewById(R.id.videoView);
        pausePlayButton = findViewById(R.id.pausePlayButton);
         
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cartoon);
        videoView.setVideoURI(videoUri);

        videoView.start();

        pausePlayButton.setOnClickListener(view -> {
            if (isPlaying) {
                videoView.pause();
                pausePlayButton.setText("Play");
            } else {
                videoView.start();
                pausePlayButton.setText("Pause");
            }
            isPlaying = !isPlaying;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }
}