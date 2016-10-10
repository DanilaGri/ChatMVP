package od.chat.presenter;

import od.chat.ui.view.UpdateUserView;

/**
 * Created by danila on 06.10.16.
 */

public abstract class UpdateUserPresenter extends BasePresenter<UpdateUserView> {
    public abstract void updateUser(String email, String password, String name, String surname,
                                String avatar);
    public abstract void setupUserInfo();
}
