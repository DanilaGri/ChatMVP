package od.chat.presenter.impl;

import javax.inject.Inject;

import od.chat.helper.CommentHelper;
import od.chat.model.Comment;
import od.chat.presenter.EditPresenter;
import rx.Observer;

/**
 * Created by danila on 23.02.17.
 */

public class EditPresenterImpl extends EditPresenter {
    private CommentHelper commentHelper;

    @Inject
    public EditPresenterImpl(CommentHelper commentHelper) {
        this.commentHelper = commentHelper;
    }

    @Override
    public void editComment(String id, String text) {
        if (subscription != null) subscription.unsubscribe();
//        view.showLoad();
        subscription = commentHelper.editComment(id, text).subscribe(new Observer<Comment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showError();
            }

            @Override
            public void onNext(Comment response) {
                view.onSuccess();
            }
        });
    }
}
