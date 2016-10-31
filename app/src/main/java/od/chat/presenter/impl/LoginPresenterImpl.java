package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.helper.AlertDialogsHelper;
import od.chat.helper.AuthHelper;
import od.chat.model.User;
import od.chat.presenter.LoginPresenter;
import od.chat.ui.Navigator;
import od.chat.ui.activity.MainActivity;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observer;

/**
 * Created by danila on 15.08.16.
 */
public class LoginPresenterImpl extends LoginPresenter {

    private Navigator navigator;
    private SharedPreferencesUtils preferencesUtils;
    private AuthHelper authHelper;
    private AlertDialogsHelper alertDialogsHelper;

    @Inject
    public LoginPresenterImpl(Navigator navigator, SharedPreferencesUtils preferencesUtils,
                              AuthHelper authHelper, AlertDialogsHelper alertDialogsHelper) {
        this.navigator = navigator;
        this.preferencesUtils = preferencesUtils;
        this.authHelper = authHelper;
        this.alertDialogsHelper = alertDialogsHelper;
    }

    @Override
    public void login(String login, String password) {
        preferencesUtils.createUserLoginSession(login, password);
        if (subscription != null) subscription.unsubscribe();
        subscription = authHelper.auth(login, password).subscribe(new Observer<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.getLocalizedMessage();
                view.showError();
            }

            @Override
            public void onNext(User user) {
                if (user == null) {
                    view.showError();
                    alertDialogsHelper.errorTxtMsg("Ошибка авторизации");
                } else {
                    navigator.openScreen(MainActivity.class);
                    preferencesUtils.saveUser(user);
                    navigator.finishActivity();
                }
            }
        });
    }

    @Override
    public void signUp() {
        navigator.openSignUp(true);
    }
}
