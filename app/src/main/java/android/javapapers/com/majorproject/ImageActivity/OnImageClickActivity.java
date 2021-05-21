package android.javapapers.com.majorproject.ImageActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.javapapers.com.majorproject.Activities.MainActivity;
import android.javapapers.com.majorproject.R;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public class OnImageClickActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    ImageView imgshow;
    int counter=0;
    Uri uri_image;
    String type_of_image,number=null;
    Button upload,click_again;
    ProgressBar progressBar;
    TextView progress_percent;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_image_click);
        imgshow=findViewById(R.id.ImagePreview);
        final Intent intent=getIntent();
        type_of_image=intent.getStringExtra("typeofdata");
        upload=findViewById(R.id.Upload);
        click_again=findViewById(R.id.ImageCLickAgain);
        progressBar=findViewById(R.id.upload_image_progressbar);
        progress_percent=findViewById(R.id.progress_percent);


        preferences=this.getSharedPreferences("imageurls",Context.MODE_PRIVATE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(this, ALLOW_KEY)) {
                showSettingsAlert();
            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)

                    != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        } else {
            final SharedPreferences sharedPreferences =getSharedPreferences("UserNumber",Context.MODE_PRIVATE);
            number=sharedPreferences.getString("number","NoData");
            number=number.substring(3);
            String name=number+"_"+type_of_image;

            click_again.setEnabled(false);
            upload.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);

            final DatabaseReference dr=FirebaseDatabase.getInstance().getReference("docs");
            dr.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(number).child("info").hasChild(type_of_image))
                    {
                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(dataSnapshot.child(number).child("info").child(type_of_image).getValue(String.class))
                                .listener(new RequestListener<Bitmap>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                        progressBar.setIndeterminate(false);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        click_again.setEnabled(true);
                                        upload.setEnabled(true);
                                        return false;

                                    }
                                })
                                .into(imgshow);
                    }
                    else {
                        try {
                            click_again.setEnabled(true);
                            upload.setEnabled(true);
                            progressBar.setIndeterminate(false);
                            progressBar.setVisibility(View.INVISIBLE);
                            openCamera();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    //
    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(OnImageClickActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(OnImageClickActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(OnImageClickActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(OnImageClickActivity.this);
                    }
                });

        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];

                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean
                                showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        this, permission);

                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(OnImageClickActivity.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        counter++;
        String imageFileName =type_of_image+"_"+number; //make a better file name
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Toast.makeText(this, imageFileName, Toast.LENGTH_SHORT).show();

        File image = new File(storageDir,imageFileName+
                ".jpg"
        );
         //Checking the file already exists and so deleting and again storing the file : code left
        uri_image = Uri.fromFile(image);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_image);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0)
        {
           if (resultCode == RESULT_OK){
               imgshow.setImageURI(uri_image);
           }
        }
    }

    public void ClickAgain(View view) {
        recreate();
    }

    public void UploadToDatabase(View view) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if (databaseReference.getDatabase().toString().isEmpty()) {
            Toast.makeText(this, "Empty database", Toast.LENGTH_SHORT).show();
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean present= true;

                        String name=number+"_"+type_of_image;
                        Toast.makeText(OnImageClickActivity.this, "Uploading", Toast.LENGTH_SHORT).show();
                        final StorageReference storageReference=FirebaseStorage.getInstance().getReference();
                        storageReference.child(number+"/info/"+type_of_image+"/"+name).putFile(uri_image).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                progressBar.setVisibility(View.VISIBLE);
                                progress_percent.setVisibility(View.VISIBLE);
                                imgshow.setAlpha((float) 0.5);
                                upload.setEnabled(false);
                                click_again.setEnabled(false);
                                double dt=(100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressBar.setProgress((int) dt);
                                progress_percent.setText((int) dt +"%");
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                                progressBar.setVisibility(View.INVISIBLE);
                                progress_percent.setVisibility(View.INVISIBLE);
                                upload.setEnabled(false);
                                click_again.setEnabled(true);
                                String name=number+"_"+type_of_image;
                                final StorageReference ref=FirebaseStorage.getInstance().getReference().child(number+"/info/"+type_of_image+"/"+name);
                                ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        Toast.makeText(OnImageClickActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Toast.makeText(OnImageClickActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
                                        databaseReference.child("docs").child(number).child("info").child(type_of_image).setValue(uri.toString());

                                        preferences.edit().putString(type_of_image.toLowerCase(),uri.toString());
                                        Toast.makeText(OnImageClickActivity.this, uri.toString(), Toast.LENGTH_LONG).show();
                                        imgshow.setAlpha((float)1);
                                        return;
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(OnImageClickActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        
    }
}