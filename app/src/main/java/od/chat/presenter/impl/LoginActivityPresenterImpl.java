package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.helper.AuthHelper;
import od.chat.model.User;
import od.chat.presenter.LoginActivityPresenter;
import od.chat.ui.Navigator;
import od.chat.ui.activity.MainActivity;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observer;

/**
 * Created by danila on 15.08.16.
 */
public class LoginActivityPresenterImpl extends LoginActivityPresenter {

    private Navigator navigator;
    private SharedPreferencesUtils preferencesUtils;
    private AuthHelper authHelper;

    @Inject
    public LoginActivityPresenterImpl(Navigator navigator, SharedPreferencesUtils preferencesUtils,
                                      AuthHelper authHelper) {
        this.navigator = navigator;
        this.preferencesUtils = preferencesUtils;
        this.authHelper = authHelper;
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
                if(user == null){
                    view.showError();
                } else {
                    navigator.openScreen(MainActivity.class);
                    navigator.finishActivity();
                }
            }
        });
    }
}
