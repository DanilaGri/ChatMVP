package od.chat.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import od.chat.ChatApp;

/**
 * Created by danila on 12.08.16.
 */
@Module
public class AppModule {
    private final ChatApp ecpApplication;

    public AppModule(ChatApp ecpApplication) {
        this.ecpApplication = ecpApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return ecpApplication;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return ecpApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    public Resources provideResource() {
        return ecpApplication.getResources();
    }

}
