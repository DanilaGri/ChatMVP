package od.chat.utils;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by danila on 12.08.16.
 */
public class SharedPreferencesUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "pass";
    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    private final SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesUtils(@NonNull SharedPreferences sharedPreferences) {

        this.sharedPreferences = sharedPreferences;
    }

    // Check for login
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }

    public void logOut() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(IS_USER_LOGIN, false);
        prefsEditor.commit();
    }

    //Create login session
    public void createUserLoginSession(String name, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }
}
