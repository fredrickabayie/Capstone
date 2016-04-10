package capstone.eduro.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import capstone.eduro.R;
import capstone.eduro.models.SessionManager;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login_btn = null;
    EditText email = null, password = null;
    TextInputLayout emailLayout, passwordLayout;

    private ProgressDialog progressDialog = null;
    private static final String TAG_RESULT = "result";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ID = "local_p_id";
    private static final String TAG_FNAME = "fname";
    private static final String TAG_LNAME = "lname";
    private SessionManager sessionManager;
    private RelativeLayout relativeLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        login_btn = (Button) findViewById(R.id.login_btn);
        assert login_btn != null;
        login_btn.setOnClickListener(this);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        emailLayout = (TextInputLayout) findViewById(R.id.emailLayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);

//        email.addTextChangedListener(new LoginValidate(email));

//        email.addTextChangedListener(new MyTextWatcher(email));

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
    }

    @Override
    public void onClick(View v) {
        if (v == login_btn) {
            readWebpage(v);
        }
    }


    private class DownloadWebPageTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            login_btn.setEnabled(false);
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        /**
         * Function to open an http connection
         *
         * @param urls The url to be sent
         * @return Returning the response
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url1 : urls) {
                try {
                    URL url = new URL(url1);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    System.out.println(url);
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(in));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        System.out.println(s);
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    assert urlConnection != null;
                    urlConnection.disconnect();
                }
            }

            return response;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            System.out.println(values[0]);
        }


        /**
         * Function to get result from the http post
         *
         * @param result Result from the post
         */
        @Override
        protected void onPostExecute(String result) {
            login_btn.setEnabled(true);
            try {
                JSONObject jsonObj = new JSONObject(result);

                String res = jsonObj.getString(TAG_RESULT);
                System.out.print(res + "\n");
                if (res.equals("1")) {

                    String patientEmail = jsonObj.getString(TAG_EMAIL);
                    String patientId = jsonObj.getString(TAG_ID);
                    String patientName = jsonObj.getString(TAG_FNAME) + " " + jsonObj.getString(TAG_LNAME);

                    sessionManager.createLoginSession(patientId, patientEmail, patientName);

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Intent home = new Intent(Login.this, Home.class);
                    startActivity(home);
                    finish();
                } else {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    String msg = jsonObj.getString("errorMessage");

                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, msg, Snackbar.LENGTH_SHORT);

                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();

                }

            } catch (JSONException jsonex) {
                jsonex.printStackTrace();
            }
        }
    }

    public void readWebpage(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();

        if (email.getText().toString().trim().length() > 0 && password.getText().toString().trim().length() > 0) {
            task.execute("http://41.79.99.165/Capstone/Project/Mobile/Server/controllers/PatientController.php?cmd=1&email="
                    + email.getText().toString().trim() + "&password=" + password.getText().toString().trim());
        } else {
            email.setError("Enter valid email address");
//            email.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); 
            password.setError("Enter valid password");
        }
    }

}