package od.chat.helper.impl;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import od.chat.R;
import od.chat.helper.AlertDialogsHelper;
import od.chat.utils.AndroidUtils;

/**
 * Created by danila on 01.10.16.
 */

public class AlertDialogsHelperImpl implements AlertDialogsHelper {

    private Activity activity;

    @Inject
    public AlertDialogsHelperImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void errorMsg(Throwable e) {
        showDialog(e);
    }

    @Override
    public void errorTxtMsg(String msg) {
        showAlertDialog(msg);
    }

    private void showDialog(Throwable e) {
        String msg;
        if (e instanceof IOException) {
            if (e instanceof UnknownHostException) {
                msg = activity.getResources().getText(R.string.error_message_network).toString();
            } else {
                msg = activity.getResources().getText(R.string.error_message_service).toString();
            }
        } else {
            msg = e.getMessage();
        }

        showAlertDialog(msg);
    }

    private void showAlertDialog(String error) {
        new AlertDialog.Builder(activity)
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        (dialog, which) -> {
                            dialog.cancel();
                        }).show();
    }
}
