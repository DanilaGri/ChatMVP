package od.chat.presenter;

import od.chat.ui.view.PrivateCabinetView;

/**
 * Created by danila on 06.10.16.
 */

public abstract class UserPresenter extends BasePresenter<PrivateCabinetView> {
    public abstract void loadUser(String idUser);

    public abstract void editUser();

    public abstract void deleteUser();
}
