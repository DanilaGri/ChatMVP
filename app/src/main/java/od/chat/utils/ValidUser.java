package od.chat.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import od.chat.R;

/**
 * Created by danila on 03.05.17.
 */

public class ValidUser {
    public static boolean setupInputValues(Context context,
                                           AutoCompleteTextView email,
                                           EditText password,
                                           EditText passwordRepeat,
                                           AutoCompleteTextView name,
                                           AutoCompleteTextView surname,
                                           AutoCompleteTextView avatar) {
        boolean flag;
        String field = "";
        if ("".equals(email.getText().toString())) {
            field = "Email";
            flag = false;
        } else if ("".equals(password.getText().toString())) {
            field = "password";
            flag = false;
        } else if ("".equals(passwordRepeat.getText().toString())) {
            field = "repeat password";
            flag = false;
        } else if ("".equals(name.getText().toString())) {
            field = "first name";
            flag = false;
        } else if ("".equals(surname.getText().toString())) {
            field = "surname";
            flag = false;
        } else if ("".equals(avatar.getText().toString())) {
            field = "avatar";
            flag = false;
        } else {

            flag = true;
        }

        if (!flag) {
            String msg;
            if (password.length() < 4) {
                msg = context.getString(R.string.error_password_length);
            } else if (!(password.getText().toString().equals(passwordRepeat.getText().toString()))) {
                msg = context.getString(R.string.error_password_match);
            } else {
                msg = context.getString(R.string.error_fill_field) + field;
            }

            new AlertDialog.Builder(context)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> {
                            }).show();
        }

        return flag;
    }
}
