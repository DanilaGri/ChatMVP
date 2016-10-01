package od.chat.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import od.chat.helper.ChatHelper;
import od.chat.helper.impl.ChatHelperImpl;
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
}
