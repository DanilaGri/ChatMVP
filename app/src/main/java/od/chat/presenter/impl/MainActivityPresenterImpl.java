package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.presenter.MainActivityPresenter;
import od.chat.ui.Navigator;
import od.chat.ui.activity.LoginActivity;

/**
 * Created by danila on 18.09.16.
 */
public class MainActivityPresenterImpl extends MainActivityPresenter {
    private Navigator navigator;

    @Inject
    public MainActivityPresenterImpl(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void openChatScreen() {
        navigator.openChatScreen();
    }

    @Override
    public void openLoginScreen() {
        navigator.openScreen(LoginActivity.class);
        navigator.finishActivity();
    }


}
