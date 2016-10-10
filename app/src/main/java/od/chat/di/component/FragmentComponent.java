package od.chat.di.component;

import android.app.Activity;

import dagger.Component;
import od.chat.di.module.FragmentModule;
import od.chat.di.qualifier.PerFragment;
import od.chat.helper.AlertDialogsHelper;
import od.chat.ui.Navigator;
import od.chat.ui.fragment.ChatFragment;
import od.chat.ui.fragment.CommentFragment;
import od.chat.ui.activity.SignUpActivity;
import od.chat.ui.fragment.PrivateCabinetFragment;
import od.chat.ui.fragment.UpdateUserFragment;

/**
 * Created by danila on 12.08.16.
 */
@PerFragment
@Component(dependencies = {ActivityComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {
    Activity getActivity();

    AlertDialogsHelper getAlertDialogsHelper();

    Navigator getNavigator();

    void inject(ChatFragment fragment);

    void inject(CommentFragment commentFragment);

    void inject(PrivateCabinetFragment privateCabinetFragment);

    void inject(UpdateUserFragment updateUserFragment);
}
