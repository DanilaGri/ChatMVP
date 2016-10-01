package od.chat.helper;

import od.chat.model.User;
import rx.Observable;

/**
 * Created by danila on 01.10.16.
 */

public interface AuthHelper {
    Observable<User> auth(String email, String password);
}
