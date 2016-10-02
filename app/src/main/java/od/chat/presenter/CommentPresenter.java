package od.chat.presenter;

import od.chat.ui.view.CommentView;

/**
 * Created by danila on 01.10.16.
 */

public abstract class CommentPresenter extends BasePresenter<CommentView> {
    public abstract void loadComments(String id);
}
