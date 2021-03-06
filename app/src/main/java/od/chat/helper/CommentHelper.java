package od.chat.helper;

import java.util.List;

import od.chat.model.Comment;
import rx.Observable;

/**
 * Created by danila on 01.10.16.
 */

public interface CommentHelper {
    Observable<List<Comment>> getComments(String id, int zero);

    Observable<String> sendComment(String user_id, String post_id, String text);

    Observable<String> deleteComment(String id);

    Observable<Comment> editComment(String id, String text);
}
