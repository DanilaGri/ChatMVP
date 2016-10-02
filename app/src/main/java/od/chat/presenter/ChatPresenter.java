package od.chat.presenter;

import od.chat.ui.view.ChatView;

/**
 * Created by danila on 18.09.16.
 */
public abstract class ChatPresenter extends BasePresenter<ChatView> {
    public abstract void loadChat();

    public abstract void openComments(String id);
}
