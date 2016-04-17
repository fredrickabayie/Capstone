package capstone.eduro.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import capstone.eduro.R;
import capstone.eduro.models.SessionManager;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, History.OnFragmentInteractionListener {

    TextView patientName;
    SessionManager sessionManager;
    ImageView patientPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences =  getSharedPreferences("my_preferences", MODE_PRIVATE);

        if(!preferences.getBoolean("onboarding_complete",false)) {
            Intent onboarding = new Intent(this, Pager.class);
            startActivity(onboarding);
            finish();
            return;
        }

        sessionManager = new SessionManager(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;

//        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_home, navigationView, false);
        navigationView.addHeaderView(headerView);

        patientPicture = (ImageView) headerView.findViewById(R.id.app_draw_patient_picture);
        patientPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editPatientDetailsIntent = new Intent(Home.this, UpdatePatient.class);
                startActivity(editPatientDetailsIntent);
            }
        });

        patientName = (TextView) headerView.findViewById(R.id.app_draw_patient_name);

        if (sessionManager.checkLogin()) {
            finish();
        } else {
            HashMap<String, String> patient = sessionManager.getPatientDetail();
            String name = patient.get(SessionManager.KEY_PATIENTNAME);
            System.out.println(name);
            if (name != null){
                patientName.setText(Html.fromHtml("<b>" + name + "</b>"));
            }
        }

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            sessionManager.logoutUser();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = getString(R.string.app_name);
        Fragment fragment = null;

        if (id == R.id.nav_medication) {
            title = "Medications";
        } else if (id == R.id.nav_health) {
            title = "Healths";
        } else if (id == R.id.nav_history) {
            fragment = new History();
            title = "History";
        } else if (id == R.id.nav_payment) {

        } else if (id == R.id.nav_share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Eduro");
            String shareMessage = "Check out Eduro a health app on the google play app store.";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share Eduro with friends."));

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_help) {

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();

            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
//        Toast.makeText(getApplicationContext(), "frag", Toast.LENGTH_LONG).show();
    }

}
