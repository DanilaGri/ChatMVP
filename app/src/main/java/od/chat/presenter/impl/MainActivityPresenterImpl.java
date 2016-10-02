package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.presenter.MainActivityPresenter;
import od.chat.ui.Navigator;
import od.chat.ui.activity.LoginActivity;
import od.chat.utils.SharedPreferencesUtils;

/**
 * Created by danila on 18.09.16.
 */
public class MainActivityPresenterImpl extends MainActivityPresenter {
    private Navigator navigator;
    private SharedPreferencesUtils preferencesUtils;

    @Inject
    public MainActivityPresenterImpl(Navigator navigator,
                                     SharedPreferencesUtils preferencesUtils) {
        this.navigator = navigator;
        this.preferencesUtils = preferencesUtils;
    }

    @Override
    public void openChatScreen() {
        navigator.openChatScreen();
    }

    @Override
    public void setupUserInfo() {
        view.setupUserInfo(preferencesUtils.getUser());
    }

    @Override
    public void openLoginScreen() {
        navigator.openScreen(LoginActivity.class);
        preferencesUtils.logOut();
        navigator.finishActivity();
    }


}
