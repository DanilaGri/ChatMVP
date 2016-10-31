package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.helper.AlertDialogsHelper;
import od.chat.helper.UserHelper;
import od.chat.model.User;
import od.chat.presenter.UserPresenter;
import od.chat.ui.Navigator;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observer;

/**
 * Created by danila on 06.10.16.
 */

public class UserPresenterImpl extends UserPresenter {
    private SharedPreferencesUtils preferencesUtils;
    private UserHelper userHelper;
    private Navigator navigator;
    private AlertDialogsHelper alertDialogsHelper;

    @Inject
    public UserPresenterImpl(SharedPreferencesUtils preferencesUtils,
                             UserHelper userHelper, Navigator navigator,
                             AlertDialogsHelper alertDialogsHelper) {
        this.preferencesUtils = preferencesUtils;
        this.userHelper = userHelper;
        this.navigator = navigator;
        this.alertDialogsHelper = alertDialogsHelper;
    }

    @Override
    public void loadUser(String idUser) {
        if (idUser != null) {
            if (subscription != null) subscription.unsubscribe();
            subscription = userHelper.readUser(idUser)
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(User user) {
                            view.showUser(user);
                        }
                    });
        } else {
            view.showUser(preferencesUtils.getUser());
        }
    }

    @Override
    public void editUser() {
        navigator.openUpdateScreen();
    }

    @Override
    public void deleteUser() {
        if (subscription != null) subscription.unsubscribe();
        subscription = userHelper.deleteUser(preferencesUtils.getUser().getId())
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
