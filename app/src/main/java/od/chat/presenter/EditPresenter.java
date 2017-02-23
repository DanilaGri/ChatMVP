package od.chat.presenter;

import od.chat.ui.view.EditView;

/**
 * Created by danila on 23.02.17.
 */

public abstract class EditPresenter extends BasePresenter<EditView> {
    public abstract void editComment(String id, String text);
}
