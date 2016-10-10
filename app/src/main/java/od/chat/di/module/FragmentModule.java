package od.chat.di.module;

import dagger.Module;
import dagger.Provides;
import od.chat.di.qualifier.PerFragment;
import od.chat.presenter.ChatPresenter;
import od.chat.presenter.CommentPresenter;
import od.chat.presenter.PrivateCabinetPresenter;
import od.chat.presenter.SignUpPresenter;
import od.chat.presenter.UpdateUserPresenter;
import od.chat.presenter.impl.ChatPresenterImpl;
import od.chat.presenter.impl.CommentPresenterImpl;
import od.chat.presenter.impl.PrivateCabinetPresenterImpl;
import od.chat.presenter.impl.SignUpPresenterImpl;
import od.chat.presenter.impl.UpdateUserPresenterImpl;

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

    @Provides
    @PerFragment
    CommentPresenter provideCommentPresenter(CommentPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    PrivateCabinetPresenter providePrivateCabinetPresenter(PrivateCabinetPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    UpdateUserPresenter provideUpdateUserPresenter(UpdateUserPresenterImpl presenter) {
        return presenter;
    }

 }
