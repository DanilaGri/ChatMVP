package od.chat.di.component;

import dagger.Subcomponent;
import od.chat.di.module.ActivityModule;
import od.chat.di.qualifier.PerActivity;
import od.chat.ui.activity.BaseActivity;
import od.chat.ui.activity.MainActivity;
import od.chat.ui.activity.StartActivity;

/**
 * Created by danila on 12.08.16.
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    FragmentComponent plusFragmentComponent();

    void inject(StartActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(BaseActivity mainActivity);
}
