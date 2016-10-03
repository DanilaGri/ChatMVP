package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.helper.AlertDialogsHelper;
import od.chat.helper.SignUpHelper;
import od.chat.presenter.SignUpPresenter;
import od.chat.ui.Navigator;
import od.chat.ui.activity.MainActivity;
import rx.Observer;

/**
 * Created by danila on 02.10.16.
 */

public class SignUpPresenterImpl extends SignUpPresenter {
    private Navigator navigator;
    private SignUpHelper helper;
    private AlertDialogsHelper alertDialogsHelper;

    @Inject
    public SignUpPresenterImpl(Navigator navigator, SignUpHelper helper,
                               AlertDialogsHelper alertDialogsHelper) {
        this.navigator = navigator;
        this.helper = helper;
        this.alertDialogsHelper = alertDialogsHelper;
    }

    @Override
    public void signUp(String email, String password, String name, String surname, String avatar) {
        if (subscription != null) subscription.unsubscribe();
        subscription = helper.createUser(email, password, name, surname, avatar)
                .subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                if ("true".equals(s)) {
                    navigator.onBackPressed();
                } else {
                    alertDialogsHelper.errorTxtMsg("Ошибка регистрации");
                }
            }
        });
    }
}
