package od.chat.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import od.chat.helper.AuthHelper;
import od.chat.helper.ChatHelper;
import od.chat.helper.CommentHelper;
import od.chat.helper.UserHelper;
import od.chat.helper.impl.AuthHelperImpl;
import od.chat.helper.impl.ChatHelperImpl;
import od.chat.helper.impl.CommentHelperImpl;
import od.chat.helper.impl.UserHelperImpl;
import od.chat.network.Api;
import od.chat.utils.RxUtil;

/**
 * Created by danila on 24.09.16.
 */
@Module(includes = {ApiModule.class, UtilsModule.class})
public class HelperModule {
    @Provides
    @Singleton
    public ChatHelper provideHandbookHelper(RxUtil rxUtil, Api api) {
        return new ChatHelperImpl(rxUtil, api);
    }

    @Provides
    @Singleton
    public AuthHelper provideAuthHelper(RxUtil rxUtil, Api api) {
        return new AuthHelperImpl(rxUtil, api);
    }

    @Provides
    @Singleton
    public CommentHelper provideCommentHelper(RxUtil rxUtil, Api api) {
        return new CommentHelperImpl(rxUtil, api);
    }

    @Provides
    @Singleton
    public UserHelper provideSignUpHelper(RxUtil rxUtil, Api api) {
        return new UserHelperImpl(rxUtil, api);
    }
}
