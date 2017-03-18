package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.presenter.MainActivityPresenter;
import od.chat.ui.Navigator;

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
}
