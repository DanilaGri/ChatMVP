package od.chat.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import od.chat.di.qualifier.PerActivity;
import od.chat.helper.AlertDialogsHelper;
import od.chat.helper.impl.AlertDialogsHelperImpl;
import od.chat.presenter.MainActivityPresenter;
import od.chat.presenter.StartActivityPresenter;
import od.chat.presenter.impl.MainActivityPresenterImpl;
import od.chat.presenter.impl.StartActivityPresenterImpl;
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
    StartActivityPresenter provideStartActivityPresenter(StartActivityPresenterImpl presenter) {
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

}
