package od.chat.ui.view;

import java.util.List;

import od.chat.model.Comment;

/**
 * Created by danila on 01.10.16.
 */

public interface CommentView extends BaseView {
    void showComments(List<Comment> commentList);
    void showLoad();
}
