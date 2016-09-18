package od.chat.presenter.impl;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import od.chat.model.Chat;
import od.chat.presenter.ChatPresenter;
import od.chat.utils.SharedPreferencesUtils;

/**
 * Created by danila on 18.09.16.
 */
public class ChatPresenterImpl extends ChatPresenter {

    private SharedPreferencesUtils preferencesUtils;

    @Inject
    public ChatPresenterImpl(SharedPreferencesUtils preferencesUtils) {
        this.preferencesUtils = preferencesUtils;
    }

    @Override
    public void loadChat() {
        Gson gson = new Gson();
        Chat list1 = null;
        Chat list2 = null;
        list1 = gson.fromJson("{\"0\":\"1\",\"id\":\"1\",\"1\":\"Title of first news\",\"title\":\"Title of first news\",\"2\":\"Description of first news\",\"description\":\"Description of first news\",\"3\":\"2016-09-02 01:23:20\",\"timestamp\":\"2016-09-02 01:23:20\",\"4\":\"Oleg\",\"user_name\":\"Oleg\",\"5\":\"Merkulov\",\"user_surname\":\"Merkulov\"}", Chat.class);
        list2 = gson.fromJson("{\"0\":\"2\",\"id\":\"2\",\"1\":\"Iron Man 3\",\"title\":\"Iron Man 3\",\"2\":\"When Tony Stark's world is torn apart by a formidable terrorist called the Mandarin, he starts an odyssey of rebuilding and retribution.\",\"description\":\"When Tony Stark's world is torn apart by a formidable terrorist called the Mandarin, he starts an odyssey of rebuilding and retribution.\",\"3\":\"2016-09-02 08:42:09\",\"timestamp\":\"2016-09-02 08:42:09\",\"4\":\"Tony\",\"user_name\":\"Tony\",\"5\":\"Stark\",\"user_surname\":\"Stark\"}", Chat.class);
        if (list1 != null && list2 != null) {
            List<Chat> list = new ArrayList<>();
            list.add(list1);
            list.add(list2);
            view.showChat(list);
        }
    }
}
