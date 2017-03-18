package od.chat.helper.impl;

import java.util.List;

import od.chat.helper.CommentHelper;
import od.chat.model.Comment;
import od.chat.network.Api;
import od.chat.utils.RxUtil;
import rx.Observable;

/**
 * Created by danila on 01.10.16.
 */

public class CommentHelperImpl implements CommentHelper {
    private Api repository;
    private RxUtil rxUtil;

    public CommentHelperImpl(RxUtil rxUtil, Api repository) {
        this.rxUtil = rxUtil;
        this.repository = repository;
    }

    @Override
    public Observable<List<Comment>> getComments(String id, int zero) {
        return repository.getComments(id, zero).compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<String> sendComment(String userId, String postId, String text) {
        return repository.sendComment(userId, postId, text).compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<String> deleteComment(String id) {
        return repository.deleteComment(id).compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<Comment> editComment(String id, String text) {
        return repository.editComment(id,text).compose(rxUtil.applySchedulers());
    }
}
