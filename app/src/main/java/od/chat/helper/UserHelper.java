package od.chat.helper;

import od.chat.model.User;
import rx.Observable;

/**
 * Created by danila on 02.10.16.
 */

public interface UserHelper {
    Observable<String> createUser(String email, String password, String name, String surname,
                                  String avatar);

    Observable<String> updateUser(String id, String email, String password, String name, String surname,
                                  String avatar);

    Observable<String> deleteUser(String id);

    Observable<User> readUser(String id);
}
