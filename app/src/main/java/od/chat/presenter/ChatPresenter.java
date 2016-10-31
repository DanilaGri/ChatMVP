package od.chat.presenter;

import od.chat.model.Chat;
import od.chat.ui.view.ChatView;

/**
 * Created by danila on 18.09.16.
 */
public abstract class ChatPresenter extends BasePresenter<ChatView> {
    public abstract void loadChat(int zero);

    public abstract void viewPost(Chat chat);

    public abstract void deletePost(String id);

    public abstract void addPost();

    public abstract void openComments(String id);

    public abstract void readUser(String id);
}
