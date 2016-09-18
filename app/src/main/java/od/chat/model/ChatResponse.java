package od.chat.model;

import java.util.List;

/**
 * Created by danila on 18.09.16.
 */
public class ChatResponse {
    private List<Chat> chatList;

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }
}
