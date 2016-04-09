package capstone.eduro.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import capstone.eduro.R;

public class UpdatePatient extends AppCompatActivity {

    FloatingActionButton updatePatient, cancelUpdatePatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        cancelUpdatePatient = (FloatingActionButton) findViewById(R.id.cancelUpdatePatient);
        assert cancelUpdatePatient != null;
        cancelUpdatePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updatePatient = (FloatingActionButton) findViewById(R.id.updatePatient);
        assert updatePatient != null;
        updatePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Your profile has been updated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
