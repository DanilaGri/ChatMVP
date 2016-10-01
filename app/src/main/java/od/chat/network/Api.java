package od.chat.network;


import java.util.List;

import od.chat.model.Chat;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by danila on 24.09.16.
 */

public interface Api {
    @GET("read_posts.php")
    Observable<List<Chat>> getChat();
}
