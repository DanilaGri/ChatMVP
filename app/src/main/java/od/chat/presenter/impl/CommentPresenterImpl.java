package od.chat.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import od.chat.helper.CommentHelper;
import od.chat.model.Comment;
import od.chat.presenter.CommentPresenter;
import od.chat.ui.Navigator;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observer;

/**
 * Created by danila on 01.10.16.
 */

public class CommentPresenterImpl extends CommentPresenter {
    private SharedPreferencesUtils preferencesUtils;
    private CommentHelper commentHelper;
    private String postId;
    private Navigator navigator;

    @Inject
    public CommentPresenterImpl(SharedPreferencesUtils preferencesUtils,
                                CommentHelper commentHelper, Navigator navigator) {
        this.preferencesUtils = preferencesUtils;
        this.commentHelper = commentHelper;
        this.navigator = navigator;
    }

    @Override
    public void loadComments(String id) {
        postId = id;
        if (subscription != null) subscription.unsubscribe();
        subscription = commentHelper.getComments(id)
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Comment> commentList) {
                        if (commentList == null) {
                            view.showComments(new ArrayList<>());
                        } else {
                            view.showComments(commentList);
                        }
                    }
                });
    }

    @Override
    public void deleteComment(String id) {
        if (subscription != null) subscription.unsubscribe();
        view.showLoad();
        subscription = commentHelper.deleteComment(id).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showError();
            }

            @Override
            public void onNext(String response) {
                loadComments(postId);
                view.update();

            }
        });
    }

    @Override
    public void editComment(String id, String text) {
        navigator.openEdit(id, text);
    }

    @Override
    public void sendComment(String text) {
        if (subscription != null) subscription.unsubscribe();
        view.showLoad();
        subscription = commentHelper.sendComment(preferencesUtils.getUser().getId(),
                postId, text).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showError();
            }

            @Override
            public void onNext(String s) {
                if (s.equals("true")) {
                    loadComments(postId);
                    view.update();
                }
            }
        });
    }

    @Override
    public String getUserId() {
        return preferencesUtils.getUser().getId();
    }
}
