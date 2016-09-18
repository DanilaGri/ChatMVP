package od.chat.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import od.chat.R;

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




}
