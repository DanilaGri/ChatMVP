package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.helper.ChatHelper;
import od.chat.model.Chat;
import od.chat.presenter.PostEditPresenter;
import od.chat.ui.Navigator;
import od.chat.utils.ProgressDialogHelper;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observer;

/**
 * Created by danila on 15.10.16.
 */

public class PostEditPresenterImpl extends PostEditPresenter {
    private Navigator navigator;
    private ChatHelper chatHelper;
    private SharedPreferencesUtils preferencesUtils;
    private ProgressDialogHelper progressDialogHelper;

    @Inject
    public PostEditPresenterImpl(Navigator navigator, ChatHelper chatHelper,
                                 SharedPreferencesUtils preferencesUtils,
                                 ProgressDialogHelper progressDialogHelper) {
        this.navigator = navigator;
        this.chatHelper = chatHelper;
        this.preferencesUtils = preferencesUtils;
        this.progressDialogHelper = progressDialogHelper;
    }

    @Override
    public void edit(String id, String title, String description, String image) {
        if (subscription != null) subscription.unsubscribe();
        subscription = chatHelper.updatePost(id, title, description, image)
                .subscribe(new Observer<Chat>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                        progressDialogHelper.hideDialog();
                    }

                    @Override
                    public void onNext(Chat response) {
                        progressDialogHelper.hideDialog();
                        navigator.onBackPressed();
                    }
                });
        progressDialogHelper.showDialog(subscription);
    }

    @Override
    public void add(String title, String description, String image) {
        if (subscription != null) subscription.unsubscribe();
        view.showLoad();
        subscription = chatHelper.addPost(preferencesUtils.getUser().getId(), title, description, image)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                    }

                    @Override
                    public void onNext(String response) {
                        navigator.onBackPressed();
                    }
                });
    }
}
