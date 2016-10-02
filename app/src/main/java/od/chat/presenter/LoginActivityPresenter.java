package od.chat.presenter;

import od.chat.ui.view.LoginActivityView;

/**
 * Created by danila on 15.08.16.
 */
public abstract class LoginActivityPresenter extends BasePresenter<LoginActivityView> {
    public abstract void login(String login, String password);
    public abstract void start();
}
