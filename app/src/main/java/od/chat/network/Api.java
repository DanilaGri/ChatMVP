package od.chat.network;


import java.util.List;

import od.chat.model.Chat;
import od.chat.model.Comment;
import od.chat.model.User;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by danila on 24.09.16.
 */

public interface Api {
    @GET("read_posts.php")
    Observable<List<Chat>> getChat();

    @GET("authentication_user.php")
    Observable<User> auth(@Query("email") String email,
                          @Query("password") String password);

    @GET("read_comments.php")
    Observable<List<Comment>> getComments(@Query("id") String id);

    @GET("create_comment.php")
    Observable<String> sendComment(@Query("user_id") String userId,
                                   @Query("post_id") String postId,
                                   @Query("text") String text);
}
