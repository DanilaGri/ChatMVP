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
    public Observable<List<Chat>> getChat() {
        return repository.getChat().compose(rxUtil.applySchedulers());
    }
}
