package od.chat.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import od.chat.ChatApp;
import od.chat.R;
import od.chat.di.HasComponent;
import od.chat.di.component.ActivityComponent;
import od.chat.di.component.DaggerActivityComponent;
import od.chat.di.module.ActivityModule;

/**
 * Created by danila on 12.08.16.
 */
public class BaseActivity extends AppCompatActivity implements HasComponent<ActivityComponent> {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
    }

    private void initActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(ChatApp.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backCount = fragmentManager.getBackStackEntryCount();

        if (backCount > 1) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    protected void setupToolbar(String title, android.support.v7.widget.Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_36dp);
        toolbar.setNavigationOnClickListener((View v) -> {
            onBackPressed();
        });
        setSupportActionBar(toolbar);
        setTitle(title);
    }

    @Override
    public ActivityComponent getComponent() {
        return activityComponent;
    }
}
