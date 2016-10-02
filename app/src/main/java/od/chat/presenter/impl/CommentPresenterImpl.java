package od.chat.presenter.impl;

import java.util.List;

import javax.inject.Inject;

import od.chat.helper.CommentHelper;
import od.chat.model.Comment;
import od.chat.presenter.CommentPresenter;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observer;

/**
 * Created by danila on 01.10.16.
 */

public class CommentPresenterImpl extends CommentPresenter {
    private SharedPreferencesUtils preferencesUtils;
    private CommentHelper commentHelper;
    private String postId;

    @Inject
    public CommentPresenterImpl(SharedPreferencesUtils preferencesUtils,
                                CommentHelper commentHelper) {
        this.preferencesUtils = preferencesUtils;
        this.commentHelper = commentHelper;
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
                        view.showComments(commentList);
                    }
                });
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
                }
            }
        });
    }
}
