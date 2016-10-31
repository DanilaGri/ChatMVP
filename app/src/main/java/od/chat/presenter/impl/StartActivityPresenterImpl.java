package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.presenter.StartActivityPresenter;
import od.chat.ui.Navigator;
import od.chat.ui.activity.MainActivity;
import od.chat.utils.SharedPreferencesUtils;

/**
 * Created by danila on 22.10.16.
 */

public class StartActivityPresenterImpl extends StartActivityPresenter {
    private Navigator navigator;
    private SharedPreferencesUtils preferencesUtils;

    @Inject
    public StartActivityPresenterImpl(Navigator navigator, SharedPreferencesUtils preferencesUtils) {
        this.navigator = navigator;
        this.preferencesUtils = preferencesUtils;
    }

    @Override
    public void start() {
        if (preferencesUtils.isUserLoggedIn()) {
            navigator.openScreen(MainActivity.class);
            navigator.finishActivity();
        } else {
            navigator.openLoginScreen();
        }
    }
}
