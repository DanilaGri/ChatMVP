package od.chat.di.module;

import dagger.Module;
import dagger.Provides;
import od.chat.di.qualifier.PerFragment;
import od.chat.presenter.ChatPresenter;
import od.chat.presenter.CommentPresenter;
import od.chat.presenter.EditPresenter;
import od.chat.presenter.LoginPresenter;
import od.chat.presenter.PostEditPresenter;
import od.chat.presenter.SignUpPresenter;
import od.chat.presenter.UpdateUserPresenter;
import od.chat.presenter.UserPresenter;
import od.chat.presenter.impl.ChatPresenterImpl;
import od.chat.presenter.impl.CommentPresenterImpl;
import od.chat.presenter.impl.EditPresenterImpl;
import od.chat.presenter.impl.LoginPresenterImpl;
import od.chat.presenter.impl.PostEditPresenterImpl;
import od.chat.presenter.impl.SignUpPresenterImpl;
import od.chat.presenter.impl.UpdateUserPresenterImpl;
import od.chat.presenter.impl.UserPresenterImpl;

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
    UserPresenter providePrivateCabinetPresenter(UserPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    UpdateUserPresenter provideUpdateUserPresenter(UpdateUserPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    PostEditPresenter providePostEditPresenter(PostEditPresenterImpl presenter) {
        return presenter;
    }


    @Provides
    @PerFragment
    SignUpPresenter provideSignUpPresenter(SignUpPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    LoginPresenter provideLoginPresenter(LoginPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    EditPresenter provideEditPresenter(EditPresenterImpl presenter) {
        return presenter;
    }
}
