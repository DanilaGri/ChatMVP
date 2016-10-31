package od.chat.presenter;

import od.chat.ui.view.LoginView;

/**
 * Created by danila on 15.08.16.
 */
public abstract class LoginPresenter extends BasePresenter<LoginView> {
    public abstract void login(String login, String password);

    public abstract void signUp();
}
