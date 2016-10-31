package od.chat.utils;

import android.view.View;

public interface AndroidUtils {
    void showKeyboard(final View view);

    void hideKeyboard(View view);

    int dpToPx(int dp);

    int pxToDp(int px);

    boolean isNetworkConnected();
}
