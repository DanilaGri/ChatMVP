package od.chat.presenter;

import od.chat.ui.view.CommentView;

/**
 * Created by danila on 01.10.16.
 */

public abstract class CommentPresenter extends BasePresenter<CommentView> {
    public abstract void loadComments(String id, int zero);

    public abstract void deleteComment(String id);

    public abstract void editComment(String id, String text);

    public abstract void sendComment(String text);

    public abstract String getUserId();
}
