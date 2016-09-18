package od.chat.di.component;

import android.app.Activity;

import dagger.Component;
import od.chat.di.module.FragmentModule;
import od.chat.di.qualifier.PerFragment;
import od.chat.ui.Navigator;
import od.chat.ui.fragment.ChatFragment;

/**
 * Created by danila on 12.08.16.
 */
@PerFragment
@Component(dependencies = {ActivityComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {
    Activity getActivity();

    Navigator getNavigator();

    void inject(ChatFragment fragment);
}
