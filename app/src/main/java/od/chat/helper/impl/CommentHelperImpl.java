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
    public Observable<List<Comment>> getComments(String id) {
        return repository.getComments(id).compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<String> sendComment(String userId, String postId, String text) {
        return repository.sendComment(userId, postId, text).compose(rxUtil.applySchedulers());
    }
}