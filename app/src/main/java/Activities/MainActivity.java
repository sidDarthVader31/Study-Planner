package Activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import Data.DataBaseHandler;
import Fragments.CompletedTaskFragment;
import Fragments.CurrentTaskFragment;
import Fragments.DefaultFragment;
import Fragments.NotCompletedTaskFragment;
import siddharthbisht.targettracker.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CurrentTaskFragment.OnFragmentInteractionListener,
        CompletedTaskFragment.OnFragmentInteractionListener, NotCompletedTaskFragment.OnFragmentInteractionListener,DefaultFragment.OnFragmentInteractionListener{
    DataBaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler=new DataBaseHandler(this);
        //byPassActivity();

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // createPopup();

                startActivity(new Intent(MainActivity.this,AddTargetActivity.class));
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //byPassFragment();
        showFragment(CurrentTaskFragment.class);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.itCurrentTask) {
            showFragment(CurrentTaskFragment.class);
        } else if (id == R.id.itCompletedTask) {
            showFragment(CompletedTaskFragment.class);

        } else if (id == R.id.itIncompleteTask) {
            showFragment(NotCompletedTaskFragment.class);
        } else if (id == R.id.itSettings) {
            startActivity(new Intent(MainActivity.this,GraphActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showFragment(Class fragmentClass) {
        android.support.v4.app.Fragment fragment=null;
        try{
            fragment= (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent,fragment,"CURRENTTASK")
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //TODO learn its implementation
    }

   public void byPassFragment(){
        if(handler.getCount()>0){
            showFragment(CurrentTaskFragment.class);
            finish();
        }
        else{
            showFragment(DefaultFragment.class);
        }
    }

}
