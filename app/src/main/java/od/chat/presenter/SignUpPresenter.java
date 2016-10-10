package od.chat.presenter;

import od.chat.ui.view.SignUpView;

/**
 * Created by danila on 02.10.16.
 */

public abstract class SignUpPresenter extends BasePresenter<SignUpView> {
    public abstract void signUp(String email, String password, String name, String surname,
                                 String avatar, boolean isSign);

    public abstract void setupUserInfo();
}
