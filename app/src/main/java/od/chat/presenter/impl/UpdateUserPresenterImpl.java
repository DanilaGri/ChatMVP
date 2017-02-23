package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.helper.AlertDialogsHelper;
import od.chat.helper.UserHelper;
import od.chat.model.User;
import od.chat.presenter.UpdateUserPresenter;
import od.chat.ui.Navigator;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observer;

/**
 * Created by danila on 06.10.16.
 */

public class UpdateUserPresenterImpl extends UpdateUserPresenter {
    private Navigator navigator;
    private SharedPreferencesUtils preferencesUtils;
    private UserHelper helper;
    private AlertDialogsHelper alertDialogsHelper;

    @Inject
    public UpdateUserPresenterImpl(Navigator navigator, SharedPreferencesUtils preferencesUtils,
                                   UserHelper helper, AlertDialogsHelper alertDialogsHelper) {
        this.navigator = navigator;
        this.preferencesUtils = preferencesUtils;
        this.helper = helper;
        this.alertDialogsHelper = alertDialogsHelper;
    }

    @Override
    public void updateUser(String email, String password, String name, String surname, String avatar) {
        if (subscription != null) subscription.unsubscribe();
        subscription = helper.updateUser(preferencesUtils.getUser().getId(), email, password,
                name, surname, avatar)
                .subscribe(new Observer<User>() {
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
                        if (user != null) {
                            preferencesUtils.saveUser(user);
                            view.onSuccessUpdate(user);
                        } else {
                            alertDialogsHelper.errorTxtMsg("Ошибка обновления");
                        }
                    }
                });
    }

    @Override
    public void setupUserInfo() {
        view.setupUser(preferencesUtils.getUser());
    }
}
