package od.chat.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import od.chat.ChatApp;
import od.chat.R;
import od.chat.di.HasComponent;
import od.chat.di.component.ActivityComponent;
import od.chat.di.component.DaggerActivityComponent;
import od.chat.di.module.ActivityModule;
import od.chat.event.UpdateUser;
import od.chat.model.User;
import od.chat.ui.Navigator;
import od.chat.utils.SharedPreferencesUtils;

/**
 * Created by danila on 12.08.16.
 */
public class BaseActivity extends AppCompatActivity implements HasComponent<ActivityComponent>, NavigationView.OnNavigationItemSelectedListener {

    private ActivityComponent activityComponent;
    DrawerLayout drawer;
    LinearLayout hView;
    NavigationView navView;
    @Inject
    Navigator navigator;
    @Inject
    SharedPreferencesUtils preferencesUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
        activityComponent.inject(this);
    }

    private void initActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(ChatApp.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            int backCount = fragmentManager.getBackStackEntryCount();

            if (backCount > 1) {
                fragmentManager.popBackStack();
            } else {
                finish();
            }
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

    protected void setupDrawer(NavigationView navView, DrawerLayout drawer, Toolbar toolbar) {
        this.drawer = drawer;
        this.navView = navView;
        navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            } else {
                //show hamburger
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.syncState();
                toolbar.setNavigationOnClickListener(v -> drawer.openDrawer(GravityCompat.START));
            }
        });
        setupUserInfo(preferencesUtils.getUser());
    }
    protected void setupUserInfo(User user) {
        if(hView == null) {
            hView = (LinearLayout) navView.inflateHeaderView(R.layout.nav_header_main);
        }
        TextView tvUser = (TextView) hView.findViewById(R.id.tv_username);
        TextView tvMail = (TextView) hView.findViewById(R.id.tv_user_mail);
        ImageView imgUser = (ImageView) hView.findViewById(R.id.image_user);
        Glide.with(this)
                .load(user.getAvatar()).asBitmap()
                .into(new BitmapImageViewTarget(imgUser) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getApplication().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imgUser.setImageDrawable(circularBitmapDrawable);
                    }
                });

        tvUser.setText(user.getName() != null && user.getSurname() != null
                ? user.getName() + " " + user.getSurname() : "");
        tvMail.setText(user.getEmail() != null ? user.getEmail() : "");
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_private_cabinet) {
            navigator.openPrivateCabinetScreen();
        } else if (id == R.id.nav_exit) {
            navigator.openLoginScreen();
        } else if (id == R.id.nav_chat) {
            navigator.openChatScreen();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
