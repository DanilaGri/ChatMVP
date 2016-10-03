package od.chat.helper.impl;

import od.chat.helper.SignUpHelper;
import od.chat.network.Api;
import od.chat.utils.RxUtil;
import rx.Observable;

/**
 * Created by danila on 02.10.16.
 */

public class SignUpHelperImpl implements SignUpHelper {

    private Api repository;
    private RxUtil rxUtil;

    public SignUpHelperImpl(RxUtil rxUtil, Api repository) {
        this.rxUtil = rxUtil;
        this.repository = repository;
    }

    @Override
    public Observable<String> createUser(String email, String password, String name,
                                         String surname, String avatar) {
        return repository.createUser(email, password, name, surname, avatar)
                .compose(rxUtil.applySchedulers());
    }
}
