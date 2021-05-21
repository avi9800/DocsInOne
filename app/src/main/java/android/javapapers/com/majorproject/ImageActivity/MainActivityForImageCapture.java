package android.javapapers.com.majorproject.ImageActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.javapapers.com.majorproject.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivityForImageCapture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_image_capture);
    }

    public void StartCamera(View view) {
        Toast.makeText(this, "Starting Camera", Toast.LENGTH_SHORT).show();
    }
}