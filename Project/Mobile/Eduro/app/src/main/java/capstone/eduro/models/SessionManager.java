package capstone.eduro.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import capstone.eduro.views.Login;

/**
 * Created by fredrickabayie on 16/11/15.
 */
public class SessionManager  {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Eduro";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Patient name
    public static final String KEY_PATIENTNAME = "patientName";

    // Patient email address
    public static final String KEY_EMAIL = "email";

    // Patient identification number
    public static final String KEY_PATIENTID = "patientId";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    /**
     * Create Login session
     */
    public void createLoginSession(String patientID, String email, String patientName) {
        // Storing Login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing patient identification number in pref
        editor.putString(KEY_PATIENTID, patientID);

        // Storing patient email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing patient name in pref
        editor.putString(KEY_PATIENTNAME, patientName);

        // commit changes
        editor.commit();
    }

    /**
     * Check Login method wil check user Login status
     * If false it will redirect user to Login page
     * Else won't do anything
     */
    public boolean checkLogin() {
        // Check Login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
            return true;
        }
        return false;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getPatientDetail() {
        HashMap<String, String> patient = new HashMap<>();
        // patient email
        patient.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // patient email id
        patient.put(KEY_PATIENTID, pref.getString(KEY_PATIENTID, null));

        // patient name
        patient.put(KEY_PATIENTNAME, pref.getString(KEY_PATIENTNAME, null));

        // return user
        return patient;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for Login
     */
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
