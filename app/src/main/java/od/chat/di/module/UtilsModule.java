package od.chat.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import od.chat.utils.AndroidUtils;
import od.chat.utils.RxUtil;
import od.chat.utils.SharedPreferencesUtils;
import od.chat.utils.impl.AndroidUtilsImpl;

/**
 * Created by danila on 12.08.16.
 */
@Module(includes = {AppModule.class})
public class UtilsModule {
    @Provides
    @Singleton
    public RxUtil provideRxUtil() {
        return new RxUtil();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application app) {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @Singleton
    SharedPreferencesUtils provideSharedPreferencesUtils(SharedPreferences prefs) {
        return new SharedPreferencesUtils(prefs);
    }

    @Provides
    @Singleton
    AndroidUtils provideAndroidUtils(AndroidUtilsImpl androidUtils) {
        return androidUtils;
    }
}

