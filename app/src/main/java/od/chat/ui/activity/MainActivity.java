package od.chat.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.model.User;
import od.chat.presenter.MainActivityPresenter;
import od.chat.ui.view.MainActivityView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    MainActivityPresenter presenter;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getComponent().inject(this);
        setupToolbar("News feed", toolbar);
        presenter.attachView(this);
        presenter.setupUserInfo();
        presenter.openChatScreen();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_exit) {
            presenter.openLoginScreen();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void setupUserInfo(User user) {
        LinearLayout hView = (LinearLayout) navView.inflateHeaderView(R.layout.nav_header_main);
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
}
