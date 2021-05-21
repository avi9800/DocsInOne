package android.javapapers.com.majorproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.javapapers.com.majorproject.R;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class verfication_of_otp extends AppCompatActivity {

    private String mVerificationId,phonenumber,name;
    private FirebaseAuth auth;
    private PinView otp;
    private Button otpcheck;
    private TextView countdowntimer;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ImageButton logout;
    private FirebaseDatabase firebaseDatabase;
    private boolean v;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity);

        otp=findViewById(R.id.otp);
        otpcheck=findViewById(R.id.otp_check);
        logout=findViewById(R.id.logout);
        countdowntimer=findViewById(R.id.countdowntimer);
        v=false;

        phonenumber=getIntent().getStringExtra("phonenumber");
        name=getIntent().getStringExtra("name");
        phonenumber="+91"+phonenumber;
        verifyphone(phonenumber);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();



        otpcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().length()<6||otp.getText().toString().isEmpty()){
                    Toast.makeText(verfication_of_otp.this, otp.getText().toString(), Toast.LENGTH_SHORT).show();
                    otp.setError("Wrong OTP Entered");
                    otp.requestFocus();
                }
                else {
                    verifyVerificationCode(otp.getText().toString());
                }
            }
        });



    }

    private void verifyphone(final String number){

        setUpverificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
               number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                resendingToken
        );

        countDownTimer=new CountDownTimer(60000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                countdowntimer.setText("00:"+String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(verfication_of_otp.this, "Try again", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(i);


            }
        }.start();

    }



   private void setUpverificationCallbacks() {
       mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


           @Override
           public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

               Log.d("vc", "OnVerificationCompleted " + phoneAuthCredential.toString());

               String otpcode = phoneAuthCredential.getSmsCode();


               if (otpcode != null) {
                   verifyVerificationCode(otpcode);
                   // progressBar.setVisibility(View.VISIBLE);
               }

           }

           @Override
           public void onVerificationFailed(FirebaseException e) {
               Toast.makeText(verfication_of_otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();


           }

           @Override
           public void onCodeSent(String code, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
               super.onCodeSent(code, forceResendingToken);
               mVerificationId = code;
               resendingToken = forceResendingToken;
           }
       };

   }

    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(auth.getCurrentUser()!=null){

                                countDownTimer.cancel();


                                final DatabaseReference reference=firebaseDatabase.getReference("users");
                                final DatabaseReference phonenum=firebaseDatabase.getReference("phnNum");
                                phonenum.child(phonenumber).child("Name").setValue(name);
                                phonenum.child(phonenumber).child("PhoneNumber").setValue(phonenumber);


                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()) {
                                            for (DataSnapshot item_snapshot : dataSnapshot.getChildren()) {

                                                    if (item_snapshot.hasChild(phonenumber)) {

                                                    Toast.makeText(verfication_of_otp.this, "Already registered", Toast.LENGTH_SHORT).show();
                                                    SharedPreferences sharedPreference=getSharedPreferences("UserNumber", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor=sharedPreference.edit();
                                                    editor.putString("number",phonenumber);
                                                    editor.commit();
                                                    v = true;
                                                }
                                            }


                                        if(v==false){


                                                reference.push().child(phonenumber).setValue(auth.getUid());

                                                SharedPreferences sharedPreference=getSharedPreferences("UserNumber", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor=sharedPreference.edit();
                                                editor.putString("number",phonenumber);
                                                editor.commit();

                                                Toast.makeText(verfication_of_otp.this, "Number is registered in database", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }
                            else{
                                Toast.makeText(verfication_of_otp.this, "User not authenticated ", Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(verfication_of_otp.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            SharedPreferences sharedPreference=getSharedPreferences("UserNumber", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreference.edit();
                            editor.putString("number",phonenumber);
                            editor.commit();
                           startActivity(i);
                            finish();
                        }
                        else {
                            String message="Something is wrong, we will fix it soon...";

                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                otp.setError("Invalid OTP Entered");
                                otp.requestFocus();
                            }

                            if(task.getException() instanceof FirebaseTooManyRequestsException){
                                message="Too many requests";
                            }

                            Toast.makeText(verfication_of_otp.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}
