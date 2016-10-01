package od.chat.helper;


import java.util.List;

import od.chat.model.Chat;
import rx.Observable;

/**
 * Created by danila on 24.09.16.
 */

public interface ChatHelper {
    Observable<List<Chat>> getChat();
}
