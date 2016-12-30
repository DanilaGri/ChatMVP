package od.chat.utils;

import rx.Subscription;

/**
 * Created by danila on 30.12.16.
 */

public interface ProgressDialogHelper {
    void showDialog(Subscription subscription);

    void hideDialog();
}
