package od.chat.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import od.chat.di.qualifier.PerActivity;
import od.chat.di.qualifier.PerFragment;
import od.chat.helper.AlertDialogsHelper;
import od.chat.helper.impl.AlertDialogsHelperImpl;
import od.chat.presenter.LoginActivityPresenter;
import od.chat.presenter.MainActivityPresenter;
import od.chat.presenter.SignUpPresenter;
import od.chat.presenter.impl.LoginActivityPresenterImpl;
import od.chat.presenter.impl.MainActivityPresenterImpl;
import od.chat.presenter.impl.SignUpPresenterImpl;
import od.chat.ui.Navigator;

/**
 * Created by danila on 26.06.16.
 */
@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @PerActivity
    Navigator provideNavigator() {
        return new Navigator(activity);
    }

    @Provides
    @PerActivity
    LoginActivityPresenter provideLoginActivityPresenter(LoginActivityPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainActivityPresenter provideMainActivityPresenter(MainActivityPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    public AlertDialogsHelper provideProgressDialogHelper(Activity activity) {
        return new AlertDialogsHelperImpl(activity);
    }

    @Provides
    @PerActivity
    SignUpPresenter provideSignUpPresenter(SignUpPresenterImpl presenter) {
        return presenter;
    }

}
