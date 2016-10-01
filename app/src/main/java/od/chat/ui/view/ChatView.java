package od.chat.ui.view;

import java.util.List;

import od.chat.model.Chat;

/**
 * Created by danila on 18.09.16.
 */
public interface ChatView extends BaseView {
    void showChat(List<Chat> chatList);
}
