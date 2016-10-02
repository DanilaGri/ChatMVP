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

    @Inject
    public CommentPresenterImpl(SharedPreferencesUtils preferencesUtils,
                                CommentHelper commentHelper) {
        this.preferencesUtils = preferencesUtils;
        this.commentHelper = commentHelper;
    }

    @Override
    public void loadComments(String id) {
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
}
