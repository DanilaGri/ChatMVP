package od.chat.helper;


import java.util.List;

import od.chat.model.Chat;
import rx.Observable;

/**
 * Created by danila on 24.09.16.
 */

public interface ChatHelper {
    Observable<List<Chat>> getChat(int zero);

    Observable<String> deletePost(String id);

    Observable<Chat> updatePost(String id, String title, String description, String image);

    Observable<String> addPost(String userId, String title, String description, String image);
}
