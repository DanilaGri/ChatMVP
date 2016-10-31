package od.chat.presenter;

import od.chat.ui.view.PostEditView;

/**
 * Created by danila on 15.10.16.
 */

public abstract class PostEditPresenter extends BasePresenter<PostEditView> {
    public abstract void edit(String id, String title, String description, String image);

    public abstract void add(String title, String description, String image);
}
