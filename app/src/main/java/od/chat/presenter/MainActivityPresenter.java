package od.chat.presenter;

import od.chat.ui.view.MainActivityView;

/**
 * Created by danila on 18.09.16.
 */
public abstract class MainActivityPresenter extends BasePresenter<MainActivityView> {
    public abstract void openChatScreen();

    public abstract void setupUserInfo();

    public abstract void openPrivateCabinet();

    public abstract void openLoginScreen();
}
