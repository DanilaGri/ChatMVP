package od.chat.ui.view;

import od.chat.model.User;

/**
 * Created by danila on 06.10.16.
 */

public interface PrivateCabinetView {
    void showUser(User user);

    void updateUser(User user);

    void showLoad();
}
