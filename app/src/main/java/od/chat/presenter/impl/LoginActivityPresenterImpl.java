package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.presenter.LoginActivityPresenter;
import od.chat.ui.Navigator;
import od.chat.ui.activity.MainActivity;
import od.chat.utils.SharedPreferencesUtils;

/**
 * Created by danila on 15.08.16.
 */
public class LoginActivityPresenterImpl extends LoginActivityPresenter {

    private Navigator navigator;
    private SharedPreferencesUtils preferencesUtils;

    @Inject
    public LoginActivityPresenterImpl(Navigator navigator, SharedPreferencesUtils preferencesUtils) {
        this.navigator = navigator;
        this.preferencesUtils = preferencesUtils;
    }

    @Override
    public void login(String login, String password) {
        preferencesUtils.createUserLoginSession(login, password);
        navigator.openScreen(MainActivity.class);
        navigator.finishActivity();
    }
}
