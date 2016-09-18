package od.chat.di.module;

import dagger.Module;
import dagger.Provides;
import od.chat.di.qualifier.PerFragment;
import od.chat.presenter.ChatPresenter;
import od.chat.presenter.impl.ChatPresenterImpl;

/**
 * Created by danila on 12.08.16.
 */
@Module
public class FragmentModule {
    @Provides
    @PerFragment
    ChatPresenter provideChatPresenter(ChatPresenterImpl presenter) {
        return presenter;
    }

}
