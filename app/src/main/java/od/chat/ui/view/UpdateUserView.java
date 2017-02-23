package od.chat.ui.view;

import od.chat.model.User;

/**
 * Created by danila on 06.10.16.
 */

public interface UpdateUserView extends BaseView {
    void setupUser(User user);
    void onSuccessUpdate(User user);
}
