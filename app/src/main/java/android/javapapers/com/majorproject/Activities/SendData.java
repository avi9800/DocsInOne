package android.javapapers.com.majorproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.javapapers.com.majorproject.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SendData extends AppCompatActivity {

    public static EditText  unique_code;
    Button confirm,scan;
    public ArrayList<String> urls,names;
    DatabaseReference sender_df;
    String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        unique_code=findViewById(R.id.unique_code);
        confirm=findViewById(R.id.send_on_unique_code);
        scan=findViewById(R.id.Scan);

        Intent i=getIntent();
        urls=i.getStringArrayListExtra("urls");
        names=i.getStringArrayListExtra("names");

//        Toast.makeText(this, ""+names.size(), Toast.LENGTH_SHORT).show();
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),scannerView.class));
            }
        });

    }

    public void send_data(View view){

        int length=urls.size();
        num=unique_code.getText().toString().trim();
        sender_df =FirebaseDatabase.getInstance().getReference("docs");
        if (num.length() != 10)
            Toast.makeText(SendData.this, "Wrong Phone Number", Toast.LENGTH_SHORT).show();
        else {
            //Toast.makeText(this, ""+names.size(), Toast.LENGTH_SHORT).show();

            for(int m=0;m<length;m++){
                //Toast.makeText(SendData.this, ""+names.size(), Toast.LENGTH_SHORT).show();
              //  Toast.makeText(SendData.this, ""+urls.size(), Toast.LENGTH_SHORT).show();
                     sender_df.child(num).child("receive").child(names.get(m)).setValue(urls.get(m));
            }
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }
    }

}