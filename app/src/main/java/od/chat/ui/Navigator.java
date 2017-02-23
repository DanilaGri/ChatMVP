package od.chat.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import od.chat.R;
import od.chat.model.Chat;
import od.chat.ui.activity.StartActivity;
import od.chat.ui.fragment.ChatFragment;
import od.chat.ui.fragment.CommentFragment;
import od.chat.ui.fragment.EditCommentFragment;
import od.chat.ui.fragment.LoginFragment;
import od.chat.ui.fragment.PostEditFragment;
import od.chat.ui.fragment.PrivateCabinetFragment;
import od.chat.ui.fragment.SignUpFragment;
import od.chat.ui.fragment.UpdateUserFragment;

/**
 * Created by danila on 15.08.16.
 */
public class Navigator {

    private Activity activity;

    @Inject
    public Navigator(Activity activity) {
        this.activity = activity;
    }

    public <T> void openScreen(Class<T> entityClass) {
        Intent intent = new Intent(activity, entityClass);
        activity.startActivity(intent);
    }

    private void addFragment(Fragment fragment, String tag) {
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, tag)
                .addToBackStack(tag).commit();
    }

    private void replaceFragment(Fragment fragment, String tag) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(tag).commit();
    }

    private void removeFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void finishActivity() {
        activity.finish();
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        int backCount = fragmentManager.getBackStackEntryCount();

        if (backCount > 1) {
            fragmentManager.popBackStack();
        } else {
            activity.finish();
        }
    }

    private FragmentManager getFragmentManager() {
        AppCompatActivity activity = (AppCompatActivity) this.activity;
        return activity.getSupportFragmentManager();
    }

    public Fragment getFragmentByTag(String tag) {
        return getFragmentManager().findFragmentByTag(tag);
    }

    public void openChatScreen() {
        ChatFragment chatFragment;
        if (getFragmentByTag(ChatFragment.TAG) != null) {
            chatFragment = (ChatFragment) getFragmentByTag(ChatFragment.TAG);
        } else {
            chatFragment = ChatFragment.newInstance("title_item");
        }
        replaceFragment(chatFragment, ChatFragment.TAG);
    }

    public void openPrivateCabinetScreen() {
        PrivateCabinetFragment fragment;
        if (getFragmentByTag(PrivateCabinetFragment.TAG) == null) {
            fragment = new PrivateCabinetFragment();
            replaceFragment(fragment, PrivateCabinetFragment.TAG);
        }
    }

    public void openReadUser(String id) {
        PrivateCabinetFragment fragment = PrivateCabinetFragment.newInstance(id);
        replaceFragment(fragment, PrivateCabinetFragment.TAG);
    }

    public void openCommentScreen(String id) {
        CommentFragment chatFragment = CommentFragment.newInstance(id);
        replaceFragment(chatFragment, CommentFragment.TAG);
    }

    public void openUpdateScreen() {
        UpdateUserFragment fragment = new UpdateUserFragment();
        replaceFragment(fragment, UpdateUserFragment.TAG);
    }

    public void openLoginScreen() {
        LoginFragment fragment = new LoginFragment();
        addFragment(fragment, LoginFragment.TAG);
    }

    public void openUpdateScreen(Chat chat) {
        PostEditFragment fragment = PostEditFragment.newInstance(chat);
        replaceFragment(fragment, PostEditFragment.TAG);
    }

    public void openSignUp(boolean isSign) {
        SignUpFragment fragment = SignUpFragment.newInstance(isSign);
        replaceFragment(fragment, SignUpFragment.TAG);
    }

    public void logout() {
        Intent intent = new Intent(activity, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void openEdit(String id, String text) {
        EditCommentFragment fragment = EditCommentFragment.newInstance(id, text);
        replaceFragment(fragment, EditCommentFragment.TAG);
    }
}
