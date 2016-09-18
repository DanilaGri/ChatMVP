package od.chat;

import android.app.Application;

import od.chat.di.HasComponent;
import od.chat.di.component.AppComponent;
import od.chat.di.component.DaggerAppComponent;
import od.chat.di.module.AppModule;

/**
 * Created by danila on 12.08.16.
 */
public class ChatApp extends Application implements HasComponent<AppComponent> {
    private AppComponent mAppComponent;
    private static ChatApp instance;

    public static ChatApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeInjector();
    }

    private void initializeInjector() {

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Override
    public AppComponent getComponent() {
        return mAppComponent;
    }
}
