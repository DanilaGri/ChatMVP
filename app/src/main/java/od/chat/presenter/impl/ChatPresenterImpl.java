package od.chat.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import od.chat.helper.ChatHelper;
import od.chat.model.Chat;
import od.chat.presenter.ChatPresenter;
import od.chat.ui.Navigator;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observer;

/**
 * Created by danila on 18.09.16.
 */
public class ChatPresenterImpl extends ChatPresenter {

    private SharedPreferencesUtils preferencesUtils;
    private ChatHelper chatHelper;
    private Navigator navigator;
    private int zero;
    private List<Chat> chatList = new ArrayList<>();

    @Inject
    public ChatPresenterImpl(SharedPreferencesUtils preferencesUtils,
                             ChatHelper chatHelper, Navigator navigator) {
        this.preferencesUtils = preferencesUtils;
        this.chatHelper = chatHelper;
        this.navigator = navigator;
    }

    @Override
    public void loadChat(int zero) {
        this.zero = zero;
//        Gson gson = new Gson();
//        Chat list1 = null;
//        Chat list2 = null;
//        list1 = gson.fromJson("{\"0\":\"1\",\"id\":\"1\",\"1\":\"Title of first news\",\"title\":\"Title of first news\",\"2\":\"Description of first news\",\"description\":\"Description of first news\",\"3\":\"2016-09-02 01:23:20\",\"timestamp\":\"2016-09-02 01:23:20\",\"4\":\"Oleg\",\"user_name\":\"Oleg\",\"5\":\"Merkulov\",\"user_surname\":\"Merkulov\"}", Chat.class);
//        list2 = gson.fromJson("{\"0\":\"2\",\"id\":\"2\",\"1\":\"Iron Man 3\",\"title\":\"Iron Man 3\",\"2\":\"When Tony Stark's world is torn apart by a formidable terrorist called the Mandarin, he starts an odyssey of rebuilding and retribution.\",\"description\":\"When Tony Stark's world is torn apart by a formidable terrorist called the Mandarin, he starts an odyssey of rebuilding and retribution.\",\"3\":\"2016-09-02 08:42:09\",\"timestamp\":\"2016-09-02 08:42:09\",\"4\":\"Tony\",\"user_name\":\"Tony\",\"5\":\"Stark\",\"user_surname\":\"Stark\"}", Chat.class);
//        if (list1 != null && list2 != null) {
//            List<Chat> list = new ArrayList<>();
//            list.add(list1);
//            list.add(list2);
//            view.showChat(list);
//        }
        if (subscription != null) subscription.unsubscribe();
        subscription = chatHelper.getChat(zero).subscribe(new Observer<List<Chat>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showError();
            }

            @Override
            public void onNext(List<Chat> chatResponse) {
                chatList.addAll(chatResponse);
                view.showChat(chatList);
            }
        });
    }

    @Override
    public void viewPost(Chat chat) {
        navigator.openUpdateScreen(chat);
    }

    @Override
    public void deletePost(String id) {
        if (subscription != null) subscription.unsubscribe();
        subscription = chatHelper.deletePost(id).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showError();
            }

            @Override
            public void onNext(String response) {
                loadChat(zero);
            }
        });
    }

    @Override
    public void addPost() {
        navigator.openUpdateScreen(null);
    }

    @Override
    public void openComments(String id) {
        navigator.openCommentScreen(id);
    }

    @Override
    public void readUser(String id) {
        navigator.openReadUser(id);
    }
}
