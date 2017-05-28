package od.chat.di.component;

import android.app.Activity;

import dagger.Component;
import dagger.Subcomponent;
import od.chat.di.module.FragmentModule;
import od.chat.di.qualifier.PerFragment;
import od.chat.helper.AlertDialogsHelper;
import od.chat.ui.Navigator;
import od.chat.ui.fragment.ChatFragment;
import od.chat.ui.fragment.CommentFragment;
import od.chat.ui.fragment.EditCommentFragment;
import od.chat.ui.fragment.LoginFragment;
import od.chat.ui.fragment.PostEditFragment;
import od.chat.ui.fragment.PrivateCabinetFragment;
import od.chat.ui.fragment.SignUpFragment;
import od.chat.ui.fragment.UpdateUserFragment;
import od.chat.utils.AndroidUtils;

/**
 * Created by danila on 12.08.16.
 */
@PerFragment
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(ChatFragment fragment);

    void inject(CommentFragment commentFragment);

    void inject(PrivateCabinetFragment privateCabinetFragment);

    void inject(UpdateUserFragment updateUserFragment);

    void inject(PostEditFragment postEditFragment);

    void inject(LoginFragment loginFragment);

    void inject(SignUpFragment signUpFragment);

    void inject(EditCommentFragment editCommentFragment);
}
