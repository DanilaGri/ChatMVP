package od.chat.helper.impl;


import java.util.List;

import od.chat.helper.ChatHelper;
import od.chat.model.Chat;
import od.chat.network.Api;
import od.chat.utils.RxUtil;
import rx.Observable;

/**
 * Created by danila on 24.09.16.
 */

public class ChatHelperImpl implements ChatHelper {
    private Api repository;
    private RxUtil rxUtil;

    public ChatHelperImpl(RxUtil rxUtil, Api repository) {
        this.rxUtil = rxUtil;
        this.repository = repository;
    }

    @Override
    public Observable<List<Chat>> getChat(int zero) {
        return repository.getChat(zero).compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<String> deletePost(String id) {
        return repository.deletePost(id).compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<Chat> updatePost(String id, String title, String description, String image) {
        return repository.updatePost(id, title, description, image).compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<String> addPost(String userId, String title, String description, String image) {
        return repository.createPost(userId, title, description, image).compose(rxUtil.applySchedulers());
    }
}
