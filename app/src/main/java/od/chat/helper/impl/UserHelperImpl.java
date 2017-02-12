package od.chat.helper.impl;

import od.chat.helper.UserHelper;
import od.chat.model.User;
import od.chat.network.Api;
import od.chat.utils.RxUtil;
import rx.Observable;

/**
 * Created by danila on 02.10.16.
 */

public class UserHelperImpl implements UserHelper {

    private Api repository;
    private RxUtil rxUtil;

    public UserHelperImpl(RxUtil rxUtil, Api repository) {
        this.rxUtil = rxUtil;
        this.repository = repository;
    }

    @Override
    public Observable<String> createUser(String email, String password, String name,
                                         String surname, String avatar) {
        return repository.createUser(email, password, name, surname, avatar)
                .compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<User> updateUser(String id, String email, String password, String name, String surname, String avatar) {
        return repository.updateUser(id, email, password, name, surname, avatar)
                .compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<String> deleteUser(String id) {
        return repository.deleteUser(id).compose(rxUtil.applySchedulers());
    }

    @Override
    public Observable<User> readUser(String id) {
        return repository.readUser(id).compose(rxUtil.applySchedulers());
    }
}
