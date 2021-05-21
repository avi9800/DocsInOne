package android.javapapers.com.majorproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.javapapers.com.majorproject.Adapters.SlideViewAdapter;
import android.javapapers.com.majorproject.Fragments.FragmentSecond;
import android.javapapers.com.majorproject.Fragments.HomeFragment;
import android.javapapers.com.majorproject.R;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentSecond.OnFragmentInteractionListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private ImageButton logout;
    public String logged_in_Status="false";
    private FirebaseAuth mauth;
    private HomeFragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();




        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle("DocsInOne");
        }
        else{
            Toast.makeText(this, "Actionbar null", Toast.LENGTH_SHORT).show();
        }
        
        logout= findViewById(R.id.logout);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth=FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();

        if(user!=null){
            logout.setVisibility(View.VISIBLE);
            Menu nav_menu=navigationView.getMenu();
            nav_menu.findItem(R.id.signup).setVisible(false);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loggingout();
                }
            });
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();


    }


    private void loggingout() {
        Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
        logout.setVisibility(View.INVISIBLE);
        Menu nav_menu=navigationView.getMenu();
        nav_menu.findItem(R.id.signup).setVisible(true);
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (menuItem.getItemId()){
            case R.id.Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                Toast.makeText(this, "Home opened", Toast.LENGTH_SHORT).show();
                return false;

            case R.id.signup:
                Toast.makeText(this, "Signup opened", Toast.LENGTH_SHORT).show();
                FragmentSecond fragment2=new FragmentSecond();
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment2);
                fragmentTransaction.commit();
                return false;

            case R.id.send:
                //if (logged_in_Status=="true")
                    startActivity(new Intent(this,Sending_Activity.class));
                //else
                  //  Toast.makeText(this, "Sign in first", Toast.LENGTH_SHORT).show();
                return false;
            case R.id.recieve:
                startActivity(new Intent(this,Recieving_Activity.class));
                return false;
            case R.id.faqs:
                if (logged_in_Status=="true")
                    Toast.makeText(this, "Faqs opened", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Sign in first", Toast.LENGTH_SHORT).show();
                return false;
            case R.id.About:
                if (logged_in_Status=="true")
                    Toast.makeText(this, "About opened", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Sign in first", Toast.LENGTH_SHORT).show();
                return false;
            case R.id.me:

                startActivity(new Intent(this,qrcodemaker.class));

                return false;


        }
        return true;
    }

    // @Override
    //public void onButtonClicked(View view) {
    //  String name=((Button) view).getText().toString();
    //switch (view.getId()){
    //  case R.id.load_fragment:
    //    Toast.makeText(this, "Id match successful", Toast.LENGTH_SHORT).show();
    //  fragmentManager=getSupportFragmentManager();
    //fragmentTransaction=fragmentManager.beginTransaction();
    //fragmentTransaction.replace(R.id.fragment_container,new Fragment_second());
    //fragmentTransaction.commit();
    //break;
    //case R.id.nextactivity:
    //  Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    //Intent i=new Intent(this,SecondActivity.class);
    //i.putExtra("buttonname",name);
    //startActivity(i);
    //break;
    //default:
    //  Toast.makeText(this, "Id mismatch", Toast.LENGTH_SHORT).show();
    //}

    //}

    public void Clicked(View v){
        String name=((Button) v).getText().toString();
        Toast.makeText(this, "Logged", Toast.LENGTH_SHORT).show();
        //Intent i = new Intent(this,SecondActivity.class);
        //i.putExtra("buttonname",name);
       // startActivity(i);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
