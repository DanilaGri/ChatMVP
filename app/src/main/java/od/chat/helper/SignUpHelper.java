package od.chat.helper;

import rx.Observable;

/**
 * Created by danila on 02.10.16.
 */

public interface SignUpHelper {
    Observable<String> createUser(String email, String password, String name, String surname,
                                  String avatar);
}
