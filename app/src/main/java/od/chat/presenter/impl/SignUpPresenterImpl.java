package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.helper.AlertDialogsHelper;
import od.chat.helper.UserHelper;
import od.chat.model.User;
import od.chat.presenter.SignUpPresenter;
import od.chat.ui.Navigator;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observer;

/**
 * Created by danila on 02.10.16.
 */

public class SignUpPresenterImpl extends SignUpPresenter {
    private Navigator navigator;
    private SharedPreferencesUtils preferencesUtils;
    private UserHelper helper;
    private AlertDialogsHelper alertDialogsHelper;

    @Inject
    public SignUpPresenterImpl(Navigator navigator, SharedPreferencesUtils preferencesUtils,
                               UserHelper helper, AlertDialogsHelper alertDialogsHelper) {
        this.navigator = navigator;
        this.helper = helper;
        this.alertDialogsHelper = alertDialogsHelper;
        this.preferencesUtils = preferencesUtils;
    }

    @Override
    public void signUp(String email, String password, String name, String surname, String avatar,
                       boolean isSign) {
        if (subscription != null) subscription.unsubscribe();
        if (isSign) {
            subscription = helper.createUser(email, password, name, surname, avatar)
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showError();
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
        } else {
            subscription = helper.updateUser(preferencesUtils.getUser().getId(), email, password,
                    name, surname, avatar)
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.getLocalizedMessage();
                        }

                        @Override
                        public void onNext(User user) {
                            if (user != null) {
                                navigator.onBackPressed();
                            } else {
                                alertDialogsHelper.errorTxtMsg("Ошибка обновления");
                            }
                        }
                    });
        }
    }

    @Override
    public void setupUserInfo() {
        view.setupUser(preferencesUtils.getUser());
    }
}
