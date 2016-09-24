package od.chat.network;

import android.database.Observable;

import od.chat.model.ChatResponse;
import retrofit2.http.GET;

/**
 * Created by danila on 24.09.16.
 */

public interface Api {
    @GET("?get_all_posts")
    Observable<ChatResponse> getChat();
}
