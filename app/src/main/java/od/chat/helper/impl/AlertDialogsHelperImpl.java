package od.chat.helper.impl;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

import java.io.IOException;

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
        String error = e.getMessage();
        if (!AndroidUtils.isNetworkConnected(activity)) {
            error = activity.getResources().getText(R.string.error_message_network).toString();
        } else if (e instanceof IOException) {
            error = activity.getResources().getText(R.string.error_message_service).toString();
        }
        showAlertDialog(error);
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
