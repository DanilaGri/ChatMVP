package od.chat.listener;

/**
 * Created by danila on 15.10.16.
 */

public interface OnChatAdapterListener extends OnAdapterListener {
    void deletePost(String id);

    void viewUser(String id);
}
