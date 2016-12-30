package od.chat.utils.impl;

import android.app.Activity;
import android.app.ProgressDialog;

import javax.inject.Inject;

import od.chat.R;
import od.chat.utils.ProgressDialogHelper;
import rx.Subscription;

/**
 * Created by danila on 30.12.16.
 */

public class ProgressDialogHelperImpl implements ProgressDialogHelper {
    private Activity activity;
    private ProgressDialog progressDialog;

    @Inject
    public ProgressDialogHelperImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void showDialog(Subscription subscription) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(activity.getString(R.string.loading));
            progressDialog.setOnCancelListener(dialog -> {
                if (subscription != null) {
                    subscription.unsubscribe();
                }
            });
        }
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
