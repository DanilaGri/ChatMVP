package od.chat.helper.impl;

import od.chat.helper.AuthHelper;
import od.chat.model.User;
import od.chat.network.Api;
import od.chat.utils.RxUtil;
import rx.Observable;

/**
 * Created by danila on 01.10.16.
 */

public class AuthHelperImpl implements AuthHelper {
    private Api repository;
    private RxUtil rxUtil;

    public AuthHelperImpl(RxUtil rxUtil, Api repository) {
        this.rxUtil = rxUtil;
        this.repository = repository;
    }

    @Override
    public Observable<User> auth(String email, String password) {
        return repository.auth(email, password).compose(rxUtil.applySchedulers());
    }
}
