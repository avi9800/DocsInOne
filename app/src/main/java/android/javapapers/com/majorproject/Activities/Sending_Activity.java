package android.javapapers.com.majorproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.javapapers.com.majorproject.Adapters.Sending_Adapter;
import android.javapapers.com.majorproject.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Sending_Activity extends AppCompatActivity implements Sending_Adapter.ItemClickListener {

    Sending_Adapter adapter;
    public  List<String> urls;
    public  List<String> names;
    Button refresh,send;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_);

        urls=new ArrayList<String>();
        names=new ArrayList<String>();
        refresh=findViewById(R.id.Refresh_images);
        send=findViewById(R.id.Send_images);

        final SharedPreferences sharedPreferences =getSharedPreferences("UserNumber", Context.MODE_PRIVATE);
        String number=sharedPreferences.getString("number","NoData");
        number=number.substring(3);
        Toast.makeText(getApplicationContext(), number, Toast.LENGTH_SHORT).show();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("docs").child(number).child("info");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                   // Toast.makeText(Sending_Activity.this, ds.getKey().toString(), Toast.LENGTH_SHORT).show();
                        names.add(ds.getKey());
                        urls.add(ds.getValue().toString());
                }

                RecyclerView recyclerView=findViewById(R.id.sending_images);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter=new Sending_Adapter(urls,names,getApplicationContext());
                recyclerView.setAdapter(adapter);

                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recreate();
                    }
                });

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getApplicationContext(),SendData.class);
                        i.putStringArrayListExtra("urls", (ArrayList<String>) urls);
                        i.putStringArrayListExtra("names", (ArrayList<String>) names);
                        startActivity(i);
                    }
                });

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }

    @Override
    public void onItemClick(View view, int position) {

    }
}