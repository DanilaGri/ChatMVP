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
//    @GET("read_posts.php")
//    Observable<List<Chat>> getChat();

    @GET("read_posts_on_range.php")
    Observable<List<Chat>> getChat(@Query("zero") int zero);

    @GET("authentication_user.php")
    Observable<User> auth(@Query("email") String email,
                          @Query("password") String password);

    @GET("read_comments.php")
    Observable<List<Comment>> getComments(@Query("id") String id);

    @GET("create_comment.php")
    Observable<String> sendComment(@Query("user_id") String userId,
                                   @Query("post_id") String postId,
                                   @Query("text") String text);

    @GET("create_user.php")
    Observable<String> createUser(@Query("email") String email,
                                  @Query("password") String password,
                                  @Query("name") String name,
                                  @Query("surname") String surname,
                                  @Query("avatar") String avatar);

    @GET("update_user.php")
    Observable<User> updateUser(@Query("id") String id,
                                @Query("email") String email,
                                @Query("password") String password,
                                @Query("name") String name,
                                @Query("surname") String surname,
                                @Query("avatar") String avatar);

    @GET("delete_user.php")
    Observable<String> deleteUser(@Query("id") String id);

    @GET("read_user.php")
    Observable<User> readUser(@Query("id") String id);

    @GET("delete_post.php")
    Observable<String> deletePost(@Query("id") String id);

    @GET("delete_comment.php")
    Observable<String> deleteComment(@Query("id") String id);

    @GET("update_post.php")
    Observable<Chat> updatePost(@Query("id") String id,
                                @Query("title") String title,
                                @Query("description") String description,
                                @Query("image") String image);

    @GET("create_post.php")
    Observable<String> createPost(@Query("user_id") String id,
                                  @Query("title") String title,
                                  @Query("description") String description,
                                  @Query("image") String image);

}
