package od.chat.di.component;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Component;
import od.chat.di.module.ApiModule;
import od.chat.di.module.AppModule;
import od.chat.di.module.HelperModule;
import od.chat.di.module.UtilsModule;
import od.chat.helper.ChatHelper;
import od.chat.utils.RxUtil;
import od.chat.utils.SharedPreferencesUtils;

/**
 * Created by danila on 12.08.16.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        HelperModule.class,
        UtilsModule.class
})
public interface AppComponent {

    Application getApplication();

    Resources getResource();

    Context getContext();

    SharedPreferencesUtils getSharedPreferencesUtils();

    RxUtil getRxUtil();

    ChatHelper getChatHelper();
}
