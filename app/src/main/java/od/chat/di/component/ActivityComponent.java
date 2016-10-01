package od.chat.di.component;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import dagger.Component;
import od.chat.di.module.ActivityModule;
import od.chat.di.qualifier.PerActivity;
import od.chat.helper.AlertDialogsHelper;
import od.chat.helper.AuthHelper;
import od.chat.helper.ChatHelper;
import od.chat.ui.Navigator;
import od.chat.ui.activity.LoginActivity;
import od.chat.ui.activity.MainActivity;
import od.chat.utils.RxUtil;
import od.chat.utils.SharedPreferencesUtils;

/**
 * Created by danila on 12.08.16.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    AlertDialogsHelper getAlertDialogsHelper();

    Activity getActivity();

    Resources getResource();

    Navigator getNavigator();

    Context getContext();

    SharedPreferencesUtils getSharedPreferencesUtils();

    RxUtil getRxUtil();

    ChatHelper getChatHelper();

    AuthHelper getAuthHelper();

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);
}
